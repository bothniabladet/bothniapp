package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.Image
import java.io.File
import java.util.*

fun Application.configureModuleUpload() {
    routing {
        route("/upload") {
            get {
                call.respond(FreeMarkerContent("upload/index.ftl", null))
            }

            post {
                val multipartData = call.receiveMultipart()
                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FileItem -> {
                            var fileName = part.originalFileName as String
                            val fileExtension = File(fileName).extension
                            val fileBytes = part.streamProvider().readBytes()

                            // Create entry for image
                            transaction {
                                val image = Image.new {
                                    caption = fileName
                                    size = fileBytes.size
                                    path = "uploads/${id}.${fileExtension}"
                                }
                                fileName = "${image.id}.${fileExtension}"
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