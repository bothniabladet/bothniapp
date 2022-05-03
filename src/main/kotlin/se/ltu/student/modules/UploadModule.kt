package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

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
                            val fileName = part.originalFileName as String
                            val fileBytes = part.streamProvider().readBytes()
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