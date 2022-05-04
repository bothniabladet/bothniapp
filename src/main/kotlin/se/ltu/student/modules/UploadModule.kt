package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.*
import se.ltu.student.plugins.UserSession
import java.io.File

fun Application.configureModuleUpload() {
    routing {
        authenticate("auth-session") {
            route("/upload") {
                get {
                    val userProfile = call.principal<UserSession>()?.userProfile ?: throw Error("Unauthorized")
                    val uploads = transaction {
                        Upload.find {
                            Uploads.user eq userProfile.id
                        }.map { it }
                    }
                    call.respond(FreeMarkerContent("upload/index.ftl", mapOf("uploads" to uploads, "uploadCount" to uploads.count())))
                }

                post {
                    // Get user
                    val userProfile = call.principal<UserSession>()?.userProfile ?: throw Error("Unauthorized")
                    val user = transaction { User.findById(userProfile.id) }

                    val upload = transaction {
                        Upload.new {
                            this.user = user
                        }
                    }

                    val multipartData = call.receiveMultipart()
                    multipartData.forEachPart { part ->
                        when (part) {
                            is PartData.FileItem -> {
                                var fileName = part.originalFileName as String
                                val fileExtension = File(fileName).extension
                                val fileBytes = part.streamProvider().readBytes()

                                // Create entry for image
                                val image = transaction {
                                    Image.new {
                                        caption = fileName
                                        size = fileBytes.size
                                        path = "uploads/${id}.${fileExtension}"
                                    }
                                }
                                fileName = "${image.id}.${fileExtension}"

                                // Register image entry to upload
                                transaction {
                                    ImageUpload.new {
                                        this.upload = upload
                                        this.image = image
                                    }
                                }

                                // Persist file to disk
                                File("uploads/$fileName").writeBytes(fileBytes)
                            }
                            else -> {}
                        }
                    }
                    call.respond(HttpStatusCode.OK)
                }
            }
        }
    }
}