package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.parseUUIDOrFail

fun Route.batchUpdateUploadImagesRoute() {
    post("/batch-update") {
        val id = getIdOrFail()

        val formParameters = call.receiveParameters()

        val category = formParameters["category"]
        val photographer = formParameters["photographer"]
        val imageSource = formParameters["imageSource"]

        transaction {
            UploadEntity.findById(id)?.images?.forEach { image ->
                if (category != null)
                    image.category = if (category != "none") CategoryEntity.findById(category.parseUUIDOrFail()) else null
                if (photographer != null)
                    image.photographer = if (photographer != "none") PhotographerEntity.findById(photographer.parseUUIDOrFail()) else null
                if (imageSource != null)
                    image.imageSource = if (imageSource != "none") ImageSourceEntity.findById(imageSource.parseUUIDOrFail()) else null
            }
        }

        setVolatileNotification(UserNotification.success("Ändringar sparade."))

        call.respondRedirect("/upload/$id")
    }
}