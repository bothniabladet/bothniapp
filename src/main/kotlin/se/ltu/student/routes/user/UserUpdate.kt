package se.ltu.student.routes.user

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.user.UserEntity
import se.ltu.student.models.user.toModel
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.updateUserRoute() {
    route("/edit") {
        get {
            val id = getIdOrFail()

            val user = transaction {
                UserEntity.findById(id)?.toModel() ?: throw Error("No such user.")
            }

            call.respondFMT(FreeMarkerContent("user/edit.ftl", mapOf("user" to user)))
        }

        post {
            val id = getIdOrFail()

            val formParameters = call.receiveParameters()

            val givenName = formParameters.getOrFail("givenName")
            val familyName = formParameters.getOrFail("familyName")

            transaction {
                val user = UserEntity.findById(id) ?: throw Error("No such user.")
                user.givenName = givenName
                user.familyName = familyName
            }

            setVolatileNotification(UserNotification.success("Ändringar sparade."))

            if (!redirectIfPossible())
                call.respondRedirect("/user")
        }
    }
}