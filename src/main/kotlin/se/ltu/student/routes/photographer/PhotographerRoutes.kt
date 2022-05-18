package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.photographerRoutes() {
    routing {
        authenticate("auth-session") {
            route("/photographer") {
                listPhotographersRoute()
                createPhotographerRoute()

                route("/{id}") {
                    photographerByIdRoute()
                    updatePhotographerRoute()
                    deletePhotographerRoute()
                }
            }
        }
    }
}