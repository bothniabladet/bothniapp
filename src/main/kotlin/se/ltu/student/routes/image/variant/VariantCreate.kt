package se.ltu.student.routes.image.variant

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageMetadata
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import java.io.File
import java.util.*

fun Route.createVariantRoute(storagePath: String) {
    route("/variant") {
        get {
            val id = getIdOrFail()

            call.respondFMT(FreeMarkerContent("image/variant.ftl", mapOf("parent" to id.toString())))
        }

        post {
            val parent = UUID.fromString(call.parameters.getOrFail("id"))

            val multipartData = call.receiveMultipart()

            multipartData.forEachPart { part ->
                when (part) {
                    is PartData.FileItem -> {
                        val fileName = part.originalFileName as String
                        val fileExtension = File(fileName).extension
                        val fileBytes = part.streamProvider().readBytes()

                        if (fileBytes.isEmpty())
                            return@forEachPart

                        // Create entry for image
                        val image = transaction {
                            ImageEntity.new {
                                val parentImage = ImageEntity.findById(parent)
                                this.parent = parentImage
                                this.caption = fileName
                                this.description = parentImage?.description
                                this.size = fileBytes.size
                                this.category = parentImage?.category
                                this.metadata = ImageMetadata(mapOf())
                            }
                        }

                        transaction {
                            image.writeImage(fileBytes, fileExtension, storagePath)
                        }
                    }
                    else -> {}
                }
            }

            setVolatileNotification(UserNotification.success("Variant tillagd."))

            call.respondRedirect("/image/${parent}")
        }
    }
}