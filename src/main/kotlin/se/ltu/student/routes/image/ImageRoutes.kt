package se.ltu.student.routes.image

import io.ktor.server.application.*
import io.ktor.server.routing.*
import se.ltu.student.routes.image.variant.imageVariantRoutes

fun Application.imageRoutes() {
    routing {
        imageByIdRoute()
        updateImageRoute()
        downloadImageRoute()
        previewImageRoute()
        deleteImageRoute()
    }

    imageVariantRoutes()
}