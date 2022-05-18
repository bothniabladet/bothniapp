package se.ltu.student.routes.image

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.toModel
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.toModel
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible
import java.util.*

fun Route.updateImageRoute() {
    route("/edit") {
        get {
            val id = getIdOrFail()

            val image = transaction {
                ImageEntity.findById(id)?.toModel()
            } ?: throw Error("Image not found.")

            val categories = transaction {
                CategoryEntity.all().map(CategoryEntity::toModel)
            }
            val photographers = transaction {
                PhotographerEntity.all().map(PhotographerEntity::toModel)
            }
            val imageSources = transaction {
                ImageSourceEntity.all().map(ImageSourceEntity::toModel)
            }

            call.respondFMT(
                FreeMarkerContent(
                    "image/edit.ftl",
                    mapOf("image" to image, "categories" to categories, "photographers" to photographers, "imageSources" to imageSources)
                )
            )
        }

        post {
            val id = getIdOrFail()

            val formParameters = call.receiveParameters()

            val caption = formParameters.getOrFail("caption")
            val description = formParameters.getOrFail("description")
            val category = formParameters.getOrFail("category")
            val photographer = formParameters.getOrFail("photographer")
            val imageSource = formParameters.getOrFail("imageSource")

            transaction {
                val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
                image.caption = caption
                image.description = description

                image.category =
                    if (category != "none") CategoryEntity.findById(UUID.fromString(category)) else null
                image.photographer =
                    if (photographer != "none") PhotographerEntity.findById(UUID.fromString(photographer)) else null
                image.imageSource =
                    if (imageSource != "none") ImageSourceEntity.findById(UUID.fromString(imageSource)) else null
            }

            setVolatileNotification(UserNotification.success("Ändringar sparade."))

            if (!redirectIfPossible())
                call.respondRedirect(call.request.uri.dropLast(5))
        }
    }
}