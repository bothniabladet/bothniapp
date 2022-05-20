package se.ltu.student.routes.user

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.user.UserEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.deleteUserRoute() {
    post("/delete") {
        val id = getIdOrFail()

        transaction {
            val user = UserEntity.findById(id) ?: throw Error("No such user.")
            user.delete()
        }

        setVolatileNotification(UserNotification.success("Användare raderad."))

        if (!redirectIfPossible())
            call.respondRedirect("/user")

    }
}