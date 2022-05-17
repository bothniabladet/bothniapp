package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.Photographer
import java.util.*

fun Application.configureModulePhotographer() {
    val storagePath: String =
        environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/photographer/{id}") {
                get {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val photographer = transaction {
                        Photographer.findById(id)?.toModel(true)
                    } ?: throw Error("Photographer not found.")

                    call.respondFMT(FreeMarkerContent("photographer/index.ftl", mapOf("photographer" to photographer)))
                }
            }
        }
    }
}
