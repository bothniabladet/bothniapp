package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.deletePhotographerRoute() {
    post("/delete") {
        val id = getIdOrFail()

        transaction {
            val photographer = PhotographerEntity.findById(id) ?: throw Error("No such photographer.")
            photographer.delete()
        }

        setVolatileNotification(UserNotification.success("Fotograf raderad."))

        if (!redirectIfPossible())
            call.respondRedirect("/photographer")
    }
}