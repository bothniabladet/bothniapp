package se.ltu.student.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*

fun Application.configureRouting() {


    routing {
        get("/") {
            call.respond(FreeMarkerContent("index.ftl", null))
        }

        authenticate("auth-session") {
            get("profile") {
                // Profile
                val userSession = call.principal<UserSession>()
                call.respond(FreeMarkerContent("profile.ftl", mapOf("user" to userSession)))
            }
        }

        route("browse") {
            // Browse
            get {
                call.respond(FreeMarkerContent("browse/index.ftl", null))
            }
        }

        route("search") {
            // Search
            get {
                call.respond(FreeMarkerContent("search.ftl", mapOf("query" to call.parameters["query"])))
            }
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
