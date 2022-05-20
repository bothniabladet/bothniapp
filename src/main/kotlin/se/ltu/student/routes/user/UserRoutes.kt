package se.ltu.student.routes.user

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.userRoutes() {
    routing {
        createUserRoute()

        authenticate("auth-session") {
            route("/user") {
                listUsersRoute()

                route("/{id}") {
                    updateUserRoute()
                    deleteUserRoute()
                }
            }
        }
    }
}