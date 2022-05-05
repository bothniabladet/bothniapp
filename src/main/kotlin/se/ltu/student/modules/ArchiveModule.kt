package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.Image
import java.io.File
import java.util.*

fun Application.configureModuleArchive() {
    routing {
        authenticate("auth-session") {
            route("/archive") {
                get {
                    call.respond(FreeMarkerContent("archive/index.ftl", null))
                }

                get("/image/{id}/preview") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val imagePath = transaction {
                        Image.findById(id)?.path
                    } ?: throw Error("Image not found.")

                    val file = File("uploads/$imagePath")

                    when (file.exists()) {
                        true -> call.respondFile(file)
                        else -> call.respond(HttpStatusCode.NotFound)
                    }
                }

                get("/image/{id}/download") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val image = transaction {
                        Image.findById(id)
                    } ?: throw Error("Image not found.")

                    // TODO: Perform additional checks

                    val imagePath = image.path
                    val file = File("uploads/$imagePath")

                    when (file.exists()) {
                        true -> call.respondFile(file)
                        else -> call.respond(HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}