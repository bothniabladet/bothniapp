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
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.redirectIfPossible

fun Route.createImageSourceRoute() {
    route("/new") {
        get {
            call.respondFMT(
                FreeMarkerContent(
                    "image-source/new.ftl",
                    null
                )
            )
        }

        post {
            val formParameters = call.receiveParameters()

            transaction {
                ImageSourceEntity.new {
                    name = formParameters.getOrFail("name")
                    website = formParameters.getOrFail("website").ifEmpty { null }
                }
            }

            setVolatileNotification(UserNotification.success("Bildkälla tillagd."))

            if (!redirectIfPossible())
                call.respondRedirect(call.request.uri.dropLast(4))
        }
    }
}