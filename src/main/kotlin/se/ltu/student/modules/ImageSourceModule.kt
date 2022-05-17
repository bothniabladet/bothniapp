package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.ImageSource
import java.util.*

fun Application.configureModuleImageSource() {
    val storagePath: String =
        environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/source/{id}") {
                get {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val imageSource = transaction {
                        ImageSource.findById(id)?.toModel(true)
                    } ?: throw Error("Image source not found.")

                    call.respondFMT(FreeMarkerContent("image-source/index.ftl", mapOf("source" to imageSource)))
                }
            }
        }
    }
}