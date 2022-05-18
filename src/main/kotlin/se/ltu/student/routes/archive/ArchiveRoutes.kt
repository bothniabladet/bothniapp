package se.ltu.student.routes.archive

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.archiveRoutes() {
    routing {
        authenticate("auth-session") {
            route("/archive") {
                listArchiveRoute()
            }
        }
    }
}