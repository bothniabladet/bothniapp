package se.ltu.student.routes.image

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.routes.getIdOrFail
import java.io.File

fun Route.downloadImageRoute(storagePath: String) {
    get("/download") {
        val id = getIdOrFail()

        val image = transaction {
            ImageEntity.findById(id)
        } ?: throw Error("Image not found.")

        // TODO: Perform additional checks.

        val file = File("uploads/${image.path ?: ""}")

        when (file.exists()) {
            true -> call.respondFile(file)
            else -> call.respond(HttpStatusCode.NotFound)
        }
    }
}