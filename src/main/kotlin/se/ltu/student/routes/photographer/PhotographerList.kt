package se.ltu.student.routes.photographer

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.PhotographerModel
import se.ltu.student.models.photographer.toModel

fun Route.listPhotographersRoute() {
    get {
        val photographers = transaction {
            PhotographerEntity.all().map(PhotographerEntity::toModel)
                .sortedBy(PhotographerModel::familyName)
        }

        call.respondFMT(
            FreeMarkerContent(
                "photographer/list.ftl",
                mapOf("photographers" to photographers)
            )
        )
    }
}