package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureModuleArchive() {
    routing {
        route("/archive") {
            get {
                call.respond(FreeMarkerContent("archive/index.ftl", null))
            }
        }
    }
}