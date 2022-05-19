package se.ltu.student.routes.imagesource

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel

fun Route.listImageSourcesRoute() {
    get {
        val imageSources = transaction {
            ImageSourceEntity.all().map(ImageSourceEntity::toModel)
        }

        call.respondFMT(
            FreeMarkerContent(
                "image-source/list.ftl",
                mapOf("imageSources" to imageSources)
            )
        )
    }
}