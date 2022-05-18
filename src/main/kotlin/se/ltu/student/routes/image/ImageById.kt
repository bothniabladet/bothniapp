package se.ltu.student.routes.image

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.toModel
import se.ltu.student.routes.getIdOrFail

fun Route.imageByIdRoute() {
    get {
        val id = getIdOrFail()

        val image = transaction {
            ImageEntity.findById(id)?.toModel()
        } ?: throw Error("Image not found.")

        call.respondFMT(FreeMarkerContent("image/index.ftl", mapOf("image" to image)))
    }
}