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
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.ImageSourceModel
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.plugins.UserNotification
import java.util.*

object ImageSourceModule {
    fun getModel(context: PipelineContext<Unit, ApplicationCall>): ImageSourceModel {
        val id = UUID.fromString(context.call.parameters.getOrFail("id"))

        return transaction {
            ImageSourceEntity.findById(id)?.toModel(true)
        } ?: throw Error("Photographer not found.")
    }

    fun getAllModels(): List<ImageSourceModel> {
        return transaction {
            ImageSourceEntity.all().map(ImageSourceEntity::toModel)
        }
    }

    suspend fun update(context: PipelineContext<Unit, ApplicationCall>) {
        val form = context.call.receiveParameters()

        transaction {
            val imageSource = ImageSourceEntity.findById(context.getIdOrFail()) ?: throw Error("No such image source.")
            imageSource.name = form.getOrFail("name")
            imageSource.website = form.getOrFail("website").ifEmpty { null }
        }
    }
}

fun Application.configureModuleImageSource() {
    routing {
        authenticate("auth-session") {
            route("/source/{id}") {
                get {
                    val imageSource = ImageSourceModule.getModel(this)

                    call.respondFMT(FreeMarkerContent("image-source/index.ftl", mapOf("imageSource" to imageSource)))
                }

                route("/edit") {
                    get {
                        val imageSource = ImageSourceModule.getModel(this)

                        call.respondFMT(FreeMarkerContent("image-source/edit.ftl", mapOf("imageSource" to imageSource)))
                    }

                    post {
                        ImageSourceModule.update(this)

                        setVolatileNotification(UserNotification.success("Ändringar sparade."))

                        if (!redirectIfPossible())
                            call.respondRedirect(call.request.uri.dropLast(5))
                    }
                }
            }
        }
    }
}