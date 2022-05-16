package se.ltu.student.modules

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.*
import se.ltu.student.plugins.UserNotification
import se.ltu.student.plugins.UserSession
import java.io.File
import java.util.*


fun Application.configureModuleUpload() {
    val storagePath: String = environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/upload") {
                get {
                    val userModel = call.principal<UserSession>()?.model ?: throw Error("Unauthorized")
                    val uploads = transaction {
                        Upload.find {
                            Uploads.user eq UUID.fromString(userModel.id)
                        }.map(Upload::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("upload/index.ftl", mapOf("uploads" to uploads)))
                }

                post {
                    // Get user
                    val userModel = call.principal<UserSession>()?.model ?: throw Error("Unauthorized")
                    val user = transaction { User.findById(UUID.fromString(userModel.id)) }
                    val images = arrayListOf<Image>()

                    val multipartData = call.receiveMultipart()
                    multipartData.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                val fileName = part.originalFileName as String
                                val fileExtension = File(fileName).extension
                                val fileBytes = part.streamProvider().readBytes()

                                if (fileBytes.isEmpty())
                                    return@forEachPart

                                // Create entry for image
                                val image = transaction {
                                    Image.new {
                                        caption = fileName
                                        size = fileBytes.size
                                        path = "${id}.${fileExtension}"
                                        metadata = ImageMetadata(mapOf())
                                    }
                                }
                                images.add(image)

                                transaction {
                                    image.writeImage(fileBytes, fileExtension, storagePath)
                                }
                            }
                            else -> {}
                        }
                    }

                    val upload = transaction {
                        Upload.new {
                            this.user = user
                            this.images = SizedCollection(images)
                        }
                    }

                    call.respondRedirect("/upload/${upload.id.value}")
                }

                get("/{id}") {
                    val id = UUID.fromString(call.parameters["id"])
                    val upload = transaction {
                        Upload.findById(id)?.toModel()
                    }
                    call.respondFMT(FreeMarkerContent("upload/manage.ftl", mapOf("upload" to upload)))
                }

                post("/{id}/delete") {
                    val id = UUID.fromString(call.parameters["id"])
                    transaction {
                        val upload = Upload.findById(id) ?: throw Error("No such upload.")
                        upload.images.forEach {
                            it.deleteImage(storagePath)
                            it.delete()
                        }
                        upload.delete()
                    }

                    call.setVolatileNotification(UserNotification.success("Uppladdning borttagen."))

                    call.respondRedirect("/upload")
                }

                post("/{id}/complete") {
                    val id = UUID.fromString(call.parameters["id"])
                    transaction {
                        Upload.findById(id)?.delete()
                    }

                    call.setVolatileNotification(UserNotification.success("Uppladdade bilder publicerade."))

                    call.respondRedirect("/upload")
                }
            }
        }
    }
}