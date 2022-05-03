package se.ltu.student.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*

fun Application.configureRouting() {


    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        route("api") {
            // API routes
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
