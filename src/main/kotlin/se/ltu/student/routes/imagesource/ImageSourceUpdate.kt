package se.ltu.student.routes.imagesource

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
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.updateImageSourceRoute() {
    route("/edit") {
        get {
            val id = getIdOrFail()

            val imageSource = transaction {
                ImageSourceEntity.findById(id)?.toModel(true)
            } ?: throw Error("Image source not found.")

            call.respondFMT(FreeMarkerContent("image-source/edit.ftl", mapOf("imageSource" to imageSource)))
        }

        post {
            val id = getIdOrFail()
            val formParameters = call.receiveParameters()

            transaction {
                val imageSource = ImageSourceEntity.findById(id) ?: throw Error("No such image source.")
                imageSource.name = formParameters.getOrFail("name")
                imageSource.website = formParameters.getOrFail("website").ifEmpty { null }
            }

            setVolatileNotification(UserNotification.success("Ändringar sparade."))

            if (!redirectIfPossible())
                call.respondRedirect(call.request.uri.dropLast(5))
        }
    }
}