package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.uploadRoutes() {
    routing {
        createUploadRoute()
        updateUploadRoute()
        deleteUploadRoute()
        publishUploadRoute()
        batchUpdateUploadImagesRoute()
    }
}