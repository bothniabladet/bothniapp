package se.ltu.student.routes.imagesource

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.imageSourceRoutes() {
    routing {
        authenticate("auth-session") {
            route("/image-source") {
                listImageSourcesRoute()
                createImageSourceRoute()

                route("/{id}") {
                    imageSourceByIdRoute()
                    updateImageSourceRoute()
                    deleteImageSourceRoute()
                }
            }
        }
    }
}
