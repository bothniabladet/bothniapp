package se.ltu.student.routes.profile

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.profileRoutes() {
    routing {
        authenticate("auth-session") {
            route("/profile") {
                showProfileRoute()
                updateProfileRoute()
            }
        }
    }
}