package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.photographerRoutes() {
    routing {
        photographerByIdRoute()
        updatePhotographerRoute()
        deletePhotographerRoute()
    }
}