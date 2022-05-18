package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.uploadRoutes() {
    val storagePath: String = environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/upload") {
                createUploadRoute(storagePath)

                route("/{id}") {
                    uploadByIdRoute()
                    deleteUploadRoute(storagePath)
                    publishUploadRoute()
                    batchUpdateUploadImagesRoute()
                }
            }
        }
    }
}