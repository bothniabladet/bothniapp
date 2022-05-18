package se.ltu.student.routes.imagesource

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.routes.getIdOrFail

fun Route.imageSourceByIdRoute() {
    get {
        val id = getIdOrFail()

        val imageSource = transaction {
            ImageSourceEntity.findById(id)?.toModel(true)
        } ?: throw Error("Image source not found.")

        call.respondFMT(FreeMarkerContent("image-source/index.ftl", mapOf("imageSource" to imageSource)))
    }
}