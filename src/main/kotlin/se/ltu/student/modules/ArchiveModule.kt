package se.ltu.student.modules

import com.drew.imaging.ImageMetadataReader
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
import se.ltu.student.models.Category
import se.ltu.student.models.Image
import se.ltu.student.models.ImageMetadata
import java.io.File
import java.util.*

fun Application.configureModuleArchive() {
    routing {
        authenticate("auth-session") {
            route("/archive") {
                get {
                    // Categories
                    // -- "events" (dates) OR image "groups"
                    call.respondFMT(FreeMarkerContent("archive/index.ftl", null))
                }

                route("/image/{id}") {
                    get {
                        val id = UUID.fromString(call.parameters.getOrFail("id"))

                        val image = transaction {
                            Image.findById(id)?.toModel()
                        } ?: throw Error("Image not found.")

                        call.respondFMT(FreeMarkerContent("image/index.ftl", mapOf("image" to image)))
                    }

                    get("/preview") {
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

                    post("/download") {
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

                                        // Create entry for image
                                        val image = transaction {
                                            Image.new {
                                                val parentImage = Image.findById(parent)
                                                this.parent = parentImage
                                                this.caption = fileName
                                                this.description = parentImage?.description
                                                this.size = fileBytes.size
                                                this.category = parentImage?.category
                                                this.metadata = ImageMetadata(mapOf())
                                            }
                                        }

                                        transaction {
                                            image.writeImage(fileBytes, fileExtension)
                                        }
                                    }
                                    else -> {}
                                }
                            }

                            call.respondRedirect("/archive/image/${parent}")
                        }
                    }

                    // Edit

                    route("/edit") {
                        get {
                            val id = UUID.fromString(call.parameters.getOrFail("id"))

                            val image = transaction {
                                Image.findById(id)?.toModel()
                            } ?: throw Error("Image not found.")
                            val categories = transaction {
                                Category.all().map(Category::toModel)
                            }

                            call.respondFMT(
                                FreeMarkerContent(
                                    "image/edit.ftl",
                                    mapOf("image" to image, "categories" to categories)
                                )
                            )
                        }

                        post {
                            val id = UUID.fromString(call.parameters.getOrFail("id"))

                            val formParameters = call.receiveParameters()

                            val caption = formParameters.getOrFail("caption")
                            val description = formParameters.getOrFail("description")
                            val category = formParameters["category"]

                            transaction {
                                val image = Image.findById(id) ?: throw Error("Image not found.")
                                image.caption = caption
                                image.description = description

                                if (category != null) {
                                    image.category = Category.findById(UUID.fromString(category))
                                }
                            }

                            call.respondRedirect("/archive/image/${id}")
                        }
                    }
                }
            }
        }
    }
}