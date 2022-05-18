package se.ltu.student.routes.source

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.sourceRoutes() {
    routing {
        sourceByIdRoute()
        updateSourceRoute()
        deleteSourceRoute()
    }
}
