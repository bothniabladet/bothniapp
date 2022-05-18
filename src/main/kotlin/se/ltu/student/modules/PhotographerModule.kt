package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import io.ktor.util.pipeline.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.ImageSource
import se.ltu.student.models.Photographer
import se.ltu.student.models.PhotographerModel
import se.ltu.student.plugins.UserNotification
import java.util.*

object PhotographerModule {
    fun getModel(context: PipelineContext<Unit, ApplicationCall>): PhotographerModel {
        val id = UUID.fromString(context.call.parameters.getOrFail("id"))

        return transaction {
            Photographer.findById(id)?.toModel(true)
        } ?: throw Error("Photographer not found.")
    }

    suspend fun update(context: PipelineContext<Unit, ApplicationCall>) {
        val form = context.call.receiveParameters()

        transaction {
            val photographer = Photographer.findById(context.getIdOrFail()) ?: throw Error("No such photographer.")
            photographer.givenName = form.getOrFail("givenName")
            photographer.familyName = form.getOrFail("familyName")

            photographer.email = form.getOrFail("email").ifEmpty { null }
            photographer.phone = form.getOrFail("phone").ifEmpty { null }

            val imageSource = form.getOrFail("imageSource")
            photographer.imageSource = if (imageSource != "none") ImageSource.findById(UUID.fromString(imageSource)) else null
        }
    }
}

fun Application.configureModulePhotographer() {
    val storagePath: String =
        environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/photographer/{id}") {
                get {
                    val photographer = PhotographerModule.getModel(this)

                    call.respondFMT(FreeMarkerContent("photographer/index.ftl", mapOf("photographer" to photographer)))
                }

                route("/edit") {
                    get {
                        val photographer = PhotographerModule.getModel(this)
                        val imageSources = ImageSourceModule.getAllModels()

                        call.respondFMT(FreeMarkerContent("photographer/edit.ftl", mapOf("photographer" to photographer, "imageSources" to imageSources)))
                    }

                    post {
                        PhotographerModule.update(this)

                        setVolatileNotification(UserNotification.success("Ändringar sparade."))

                        if (!redirectIfPossible())
                            call.respondRedirect(call.request.uri.dropLast(5))
                    }
                }
            }
        }
    }
}
