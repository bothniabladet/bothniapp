package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureModuleSearch() {
    routing {
        route("/search") {
            get {
                call.respond(FreeMarkerContent("search.ftl", mapOf("query" to call.parameters["query"])))
            }
        }
    }
}