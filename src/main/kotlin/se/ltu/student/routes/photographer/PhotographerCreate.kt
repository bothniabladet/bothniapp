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
import se.ltu.student.routes.redirectIfPossible
import java.util.*

fun Route.createPhotographerRoute() {
    route("/new") {
        get {
            val imageSources = transaction {
                ImageSourceEntity.all().map(ImageSourceEntity::toModel)
            }

            call.respondFMT(
                FreeMarkerContent(
                    "photographer/new.ftl",
                    mapOf("imageSources" to imageSources)
                )
            )
        }

        post {
            val formParameters = call.receiveParameters()

            transaction {
                PhotographerEntity.new {
                    givenName = formParameters.getOrFail("givenName")
                    familyName = formParameters.getOrFail("familyName")

                    email = formParameters.getOrFail("email").ifEmpty { null }
                    phone = formParameters.getOrFail("phone").ifEmpty { null }

                    val imageSource = formParameters.getOrFail("imageSource")
                    this.imageSource =
                        if (imageSource != "none") ImageSourceEntity.findById(UUID.fromString(imageSource)) else null
                }
            }

            setVolatileNotification(UserNotification.success("Fotograf tillagd."))

            if (!redirectIfPossible())
                call.respondRedirect(call.request.uri.dropLast(4))

        }
    }
}