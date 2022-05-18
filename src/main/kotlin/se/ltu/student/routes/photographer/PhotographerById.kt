package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.toModel
import se.ltu.student.routes.getIdOrFail

fun Route.photographerByIdRoute() {
    get {
        val id = getIdOrFail()

        val photographer = transaction {
            PhotographerEntity.findById(id)?.toModel(true)
        } ?: throw Error("Photographer not found.")

        call.respondFMT(FreeMarkerContent("photographer/index.ftl", mapOf("photographer" to photographer)))
    }
}