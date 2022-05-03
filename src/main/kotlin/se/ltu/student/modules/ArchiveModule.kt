package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import java.io.File

fun Application.configureModuleArchive() {
    routing {
        route("/archive") {
            get {
                call.respond(FreeMarkerContent("archive/index.ftl", null))
            }

            get("/download/{image}.{extension}") {
                val image = call.parameters.getOrFail("image")
                val extension = call.parameters.getOrFail("extension")
                val file = File("uploads/$image.$extension")

                when (file.exists()) {
                    true -> call.respondFile(file)
                    else -> call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}