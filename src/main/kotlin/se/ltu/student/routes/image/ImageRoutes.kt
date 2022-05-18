package se.ltu.student.routes.image

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*
import se.ltu.student.routes.image.variant.imageVariantRoutes

fun Application.imageRoutes() {
    val storagePath: String = environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/image/{id}") {
                imageByIdRoute()
                updateImageRoute()
                downloadImageRoute(storagePath)
                previewImageRoute(storagePath)
                deleteImageRoute(storagePath)

                imageVariantRoutes(storagePath)
            }
        }
    }
}