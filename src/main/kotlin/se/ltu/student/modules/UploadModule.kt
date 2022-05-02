package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureModuleUpload() {
    routing {
        route("/upload") {
            get {
                call.respond(FreeMarkerContent("upload/index.ftl", null))
            }
        }
    }
}