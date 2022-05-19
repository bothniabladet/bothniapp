package se.ltu.student.routes.imagesource

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.deleteImageSourceRoute() {
    post("/delete") {
        val id = getIdOrFail()

        transaction {
            ImageSourceEntity.findById(id)?.delete()
        }

        setVolatileNotification(UserNotification.success("Bildkälla raderad."))

        if (!redirectIfPossible())
            call.respondRedirect("/image-source")
    }
}