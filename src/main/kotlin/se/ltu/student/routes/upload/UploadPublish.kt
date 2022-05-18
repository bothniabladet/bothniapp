package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail

fun Route.publishUploadRoute() {
    post("/publish") {
        val id = getIdOrFail()
        transaction {
            UploadEntity.findById(id)?.delete()
        }

        setVolatileNotification(UserNotification.success("Uppladdade bilder publicerade."))

        call.respondRedirect("/upload")
    }
}