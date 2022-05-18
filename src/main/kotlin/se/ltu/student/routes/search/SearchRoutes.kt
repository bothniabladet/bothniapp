package se.ltu.student.routes.search

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*


fun Application.searchRoutes() {
    routing {
        authenticate("auth-session") {
            route("/search") {
                searchByQuery()
            }
        }
    }
}