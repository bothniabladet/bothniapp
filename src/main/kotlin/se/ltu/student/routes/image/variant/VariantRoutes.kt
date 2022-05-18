package se.ltu.student.routes.image.variant

import io.ktor.server.routing.*

fun Route.imageVariantRoutes(storagePath: String) {
    createVariantRoute(storagePath)
    decoupleVariantRoute()
}