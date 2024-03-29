package se.ltu.student.routes.image

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.deleteImageRoute(storagePath: String) {
    post("/delete") {
        val id = getIdOrFail()

        transaction {
            val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
            image.deleteImage(storagePath)
            image.delete()
        }

        setVolatileNotification(UserNotification.success("Bild raderad."))

        if (!redirectIfPossible())
            call.respondRedirect("/")
    }
}