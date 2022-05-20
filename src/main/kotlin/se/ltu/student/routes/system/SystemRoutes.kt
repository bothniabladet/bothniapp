package se.ltu.student.routes.system

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.systemRoutes() {
    routing {
        authenticate("auth-session") {
            route("/system") {
                systemOverviewRoute()
            }
        }
    }
}