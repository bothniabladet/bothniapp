package se.ltu.student.routes.image.variant

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.imageVariantRoutes() {
    routing {
        createVariantRoute()
    }
}