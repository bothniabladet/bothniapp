package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail

fun Route.deleteUploadRoute(storagePath: String) {
    post("/delete") {
        val id = getIdOrFail()
        transaction {
            val upload = UploadEntity.findById(id) ?: throw Error("No such upload.")
            upload.images.forEach {
                it.deleteImage(storagePath)
                it.delete()
            }
            upload.delete()
        }

        setVolatileNotification(UserNotification.success("Uppladdning raderad."))

        call.respondRedirect("/upload")
    }
}