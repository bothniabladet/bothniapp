package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible
import java.util.*

fun Route.updatePhotographerRoute() {
    route("/edit") {
        get {
            val id = getIdOrFail()

            val photographer = transaction {
                PhotographerEntity.findById(id)
            }

            val imageSources = transaction {
                ImageSourceEntity.all().map(ImageSourceEntity::toModel)
            }

            call.respondFMT(
                FreeMarkerContent(
                    "photographer/edit.ftl",
                    mapOf("photographer" to photographer, "imageSources" to imageSources)
                )
            )
        }

        post {
            val id = getIdOrFail()
            val formParameters = call.receiveParameters()

            transaction {
                val photographer = PhotographerEntity.findById(id) ?: throw Error("No such photographer.")
                photographer.givenName = formParameters.getOrFail("givenName")
                photographer.familyName = formParameters.getOrFail("familyName")

                photographer.email = formParameters.getOrFail("email").ifEmpty { null }
                photographer.phone = formParameters.getOrFail("phone").ifEmpty { null }

                val imageSource = formParameters.getOrFail("imageSource")
                photographer.imageSource =
                    if (imageSource != "none") ImageSourceEntity.findById(UUID.fromString(imageSource)) else null
            }

            setVolatileNotification(UserNotification.success("Ändringar sparade."))

            if (!redirectIfPossible())
                call.respondRedirect(call.request.uri.dropLast(5))
        }
    }
}