package se.ltu.student.routes.upload

import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageMetadata
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.models.upload.UploadTable
import se.ltu.student.models.upload.toModel
import se.ltu.student.models.user.UserEntity
import se.ltu.student.routes.getAuthenticatedUserIdOrFail
import java.io.File

fun Route.createUploadRoute(storagePath: String) {
    get {
        val userId = getAuthenticatedUserIdOrFail()

        val uploads = transaction {
            UploadEntity.find {
                UploadTable.user eq userId
            }.map(UploadEntity::toModel)
        }

        call.respondFMT(FreeMarkerContent("upload/index.ftl", mapOf("uploads" to uploads)))
    }

    post {
        val userId = getAuthenticatedUserIdOrFail()
        val user = transaction { UserEntity.findById(userId) }
        val images = arrayListOf<ImageEntity>()

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
                            caption = fileName
                            size = fileBytes.size
                            path = "${id}.${fileExtension}"
                            metadata = ImageMetadata(mapOf())
                        }
                    }
                    images.add(image)

                    // Set metadata and write image to disk
                    transaction {
                        image.writeImage(fileBytes, fileExtension, storagePath)
                    }
                }
                else -> {}
            }
        }

        val upload = transaction {
            UploadEntity.new {
                this.user = user
                this.images = SizedCollection(images)
            }
        }

        call.respondRedirect("/upload/${upload.id.value}")
    }
}