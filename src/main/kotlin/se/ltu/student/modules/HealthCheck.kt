package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.moduleHealthCheck() {
    routing {
        get("/health") {
            call.respondText("OK")
        }
    }
}