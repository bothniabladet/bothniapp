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
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageMetadata
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.toModel
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.models.upload.UploadTable
import se.ltu.student.models.upload.toModel
import se.ltu.student.models.user.UserEntity
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
                        UploadEntity.find {
                            UploadTable.user eq UUID.fromString(userModel.id)
                        }.map(UploadEntity::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("upload/index.ftl", mapOf("uploads" to uploads)))
                }

                post {
                    // Get user
                    val userModel = call.principal<UserSession>()?.model ?: throw Error("Unauthorized")
                    val user = transaction { UserEntity.findById(UUID.fromString(userModel.id)) }
                    val images = arrayListOf<ImageEntity>()

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
                                    ImageEntity.new {
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
                        UploadEntity.new {
                            this.user = user
                            this.images = SizedCollection(images)
                        }
                    }

                    call.respondRedirect("/upload/${upload.id.value}")
                }

                get("/{id}") {
                    val id = UUID.fromString(call.parameters["id"])
                    val upload = transaction {
                        UploadEntity.findById(id)?.toModel()
                    }

                    val categories = transaction {
                        CategoryEntity.all().map(CategoryEntity::toModel)
                    }
                    val photographers = transaction {
                        PhotographerEntity.all().map(PhotographerEntity::toModel)
                    }
                    val imageSources = transaction {
                        ImageSourceEntity.all().map(ImageSourceEntity::toModel)
                    }

                    call.respondFMT(FreeMarkerContent("upload/manage.ftl", mapOf("upload" to upload, "categories" to categories, "photographers" to photographers, "imageSources" to imageSources)))
                }

                post("/{id}/apply") {
                    val id = UUID.fromString(call.parameters["id"])

                    val formParameters = call.receiveParameters()

                    val category = formParameters["category"]
                    val photographer = formParameters["photographer"]
                    val imageSource = formParameters["imageSource"]

                    transaction {
                        UploadEntity.findById(id)?.images?.forEach { image ->
                            if (category != null)
                                image.category = if (category != "none") CategoryEntity.findById(UUID.fromString(category)) else null
                            if (photographer != null)
                                image.photographer = if (photographer != "none") PhotographerEntity.findById(UUID.fromString(photographer)) else null
                            if (imageSource != null)
                                image.imageSource = if (imageSource != "none") ImageSourceEntity.findById(UUID.fromString(imageSource)) else null
                        }
                    }

                    setVolatileNotification(UserNotification.success("Ändringar sparade."))

                    call.respondRedirect("/upload/$id")
                }

                post("/{id}/delete") {
                    val id = UUID.fromString(call.parameters["id"])
                    transaction {
                        val upload = UploadEntity.findById(id) ?: throw Error("No such upload.")
                        upload.images.forEach {
                            it.deleteImage(storagePath)
                            it.delete()
                        }
                        upload.delete()
                    }

                    setVolatileNotification(UserNotification.success("Uppladdning borttagen."))

                    call.respondRedirect("/upload")
                }

                post("/{id}/publish") {
                    val id = UUID.fromString(call.parameters["id"])
                    transaction {
                        UploadEntity.findById(id)?.delete()
                    }

                    setVolatileNotification(UserNotification.success("Uppladdade bilder publicerade."))

                    call.respondRedirect("/upload")
                }
            }
        }
    }
}