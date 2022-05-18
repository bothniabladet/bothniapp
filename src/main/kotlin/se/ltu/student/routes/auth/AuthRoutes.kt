package se.ltu.student.routes.auth

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.authRoutes() {
    routing {
        loginRoute()
        logoutRoute()
    }
}