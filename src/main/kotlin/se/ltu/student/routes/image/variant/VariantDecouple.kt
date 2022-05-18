package se.ltu.student.routes.image.variant

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.decoupleVariantRoute() {
    post("/decouple") {
        val id = getIdOrFail()

        transaction {
            val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
            image.parent = null
        }

        setVolatileNotification(UserNotification.success("Bild frikopplad."))

        if (!redirectIfPossible())
            call.respondRedirect("/image/${id}")
    }
}