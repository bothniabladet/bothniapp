package se.ltu.student.routes.user

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.userRoutes() {
    routing {
        userProfileRoute()
        createUserRoute()
        updateUserRoute()
    }
}