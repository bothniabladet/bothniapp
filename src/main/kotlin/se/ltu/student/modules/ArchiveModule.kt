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
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.*
import se.ltu.student.plugins.UserNotification
import java.io.File
import java.util.*

fun Application.configureModuleArchive() {
    val storagePath: String = environment.config.propertyOrNull("ktor.deployment.storagePath")?.getString() ?: "/uploads"

    routing {
        authenticate("auth-session") {
            route("/archive") {
                get {
                    // Categories
                    // -- "events" (dates) OR image "groups"
                    val categories = transaction {
                        Category.all().map(Category::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("archive/index.ftl", mapOf("categories" to categories)))
                }

                get("/{slug}") {
                    val slug = call.parameters.getOrFail("slug")

                    if (slug == "uncategorized") {
                        val category = CategoryModel("uncategorized", null, "Okategoriserat", "uncategorized", "Bilder som inte tillhör en kategori")
                        val images = transaction {
                            Image.find {
                                (Images.category eq null) and (Images.id notInSubQuery ImageUploads.slice(ImageUploads.image).selectAll())
                            }.map(Image::toModel)
                        }
                        call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
                        return@get
                    }

                    val category = transaction {
                        Category.find(Categories.slug eq slug).firstOrNull() ?: Category.findById(UUID.fromString(slug)) ?: throw Error("No such category.")
                    }
                    val images = transaction {
                        Image.find {
                            (Images.category eq category.id) and (Images.parent eq null) and (Images.id notInSubQuery ImageUploads.slice(ImageUploads.image).selectAll())
                        }.map(Image::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
                }
            }
        }
    }
}