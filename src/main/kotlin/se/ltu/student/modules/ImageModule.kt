package se.ltu.student.modules

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.*
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageMetadata
import se.ltu.student.models.image.toModel
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.toModel
import se.ltu.student.plugins.UserNotification
import java.io.File
import java.util.*

fun Application.configureModuleImage() {
    val storagePath: String =
        environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/image/{id}") {
                get {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val image = transaction {
                        ImageEntity.findById(id)?.toModel()
                    } ?: throw Error("Image not found.")

                    call.respondFMT(FreeMarkerContent("image/index.ftl", mapOf("image" to image)))
                }

                get("/preview") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val imagePath = transaction {
                        ImageEntity.findById(id)?.path
                    } ?: throw Error("Image not found.")

                    val file = File("uploads/$imagePath")

                    when (file.exists()) {
                        true -> call.respondFile(file)
                        else -> call.respond(HttpStatusCode.NotFound)
                    }
                }

                post("/download") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    val image = transaction {
                        ImageEntity.findById(id)
                    } ?: throw Error("Image not found.")

                    // TODO: Perform additional checks

                    val imagePath = image.path
                    val file = File("uploads/$imagePath")

                    when (file.exists()) {
                        true -> call.respondFile(file)
                        else -> call.respond(HttpStatusCode.NotFound)
                    }
                }

                // Variation

                route("/variant") {
                    get {
                        val id = UUID.fromString(call.parameters.getOrFail("id"))

                        call.respondFMT(FreeMarkerContent("image/variant.ftl", mapOf("parent" to id.toString())))
                    }

                    post {
                        val parent = UUID.fromString(call.parameters.getOrFail("id"))

                        val multipartData = call.receiveMultipart()

                        multipartData.forEachPart { part ->
                            when (part) {
                                is PartData.FileItem -> {
                                    var fileName = part.originalFileName as String
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

                // Edit

                route("/edit") {
                    get {
                        val id = UUID.fromString(call.parameters.getOrFail("id"))

                        val image = transaction {
                            ImageEntity.findById(id)?.toModel()
                        } ?: throw Error("Image not found.")

                        val categories = transaction {
                            CategoryEntity.all().map(CategoryEntity::toModel)
                        }
                        val photographers = transaction {
                            PhotographerEntity.all().map(PhotographerEntity::toModel)
                        }
                        val imageSources = transaction {
                            ImageSourceEntity.all().map(ImageSourceEntity::toModel)
                        }

                        call.respondFMT(
                            FreeMarkerContent(
                                "image/edit.ftl",
                                mapOf("image" to image, "categories" to categories, "photographers" to photographers, "imageSources" to imageSources)
                            )
                        )
                    }

                    post {
                        val id = getIdOrFail()

                        val formParameters = call.receiveParameters()

                        val caption = formParameters.getOrFail("caption")
                        val description = formParameters.getOrFail("description")
                        val category = formParameters.getOrFail("category")
                        val photographer = formParameters.getOrFail("photographer")
                        val imageSource = formParameters.getOrFail("imageSource")

                        transaction {
                            val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
                            image.caption = caption
                            image.description = description

                            image.category =
                                if (category != "none") CategoryEntity.findById(UUID.fromString(category)) else null
                            image.photographer =
                                if (photographer != "none") PhotographerEntity.findById(UUID.fromString(photographer)) else null
                            image.imageSource =
                                if (imageSource != "none") ImageSourceEntity.findById(UUID.fromString(imageSource)) else null
                        }

                        setVolatileNotification(UserNotification.success("Ändringar sparade."))

                        if (!redirectIfPossible())
                            call.respondRedirect(call.request.uri.dropLast(5))
                    }
                }

                // Decouple

                post("/decouple") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    transaction {
                        val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
                        image.parent = null
                    }

                    setVolatileNotification(UserNotification.success("Bild frikopplad."))

                    call.respondRedirect("/image/${id}")
                }

                // Delete

                post("/delete") {
                    val id = UUID.fromString(call.parameters.getOrFail("id"))

                    transaction {
                        val image = ImageEntity.findById(id) ?: throw Error("Image not found.")
                        image.deleteImage(storagePath)
                        image.delete()
                    }

                    setVolatileNotification(UserNotification.success("Bild borttagen."))

                    val redirect = call.parameters["redirect"]
                    if (redirect != null)
                        call.respondRedirect(redirect)
                    else
                        call.respondRedirect("/image/${id}")

                    call.respondRedirect("/")
                }
            }
        }
    }
}