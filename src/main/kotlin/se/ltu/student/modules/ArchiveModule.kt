package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.CategoryModel
import se.ltu.student.models.category.CategoryTable
import se.ltu.student.models.category.toModel
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable
import se.ltu.student.models.image.toModel
import se.ltu.student.models.upload.ImageUploadTable
import java.util.*

fun Application.configureModuleArchive() {
    routing {
        authenticate("auth-session") {
            route("/archive") {
                get {
                    // Categories
                    // -- "events" (dates) OR image "groups"
                    val categories = transaction {
                        CategoryEntity.all().map(CategoryEntity::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("archive/index.ftl", mapOf("categories" to categories)))
                }

                get("/{slug}") {
                    val slug = call.parameters.getOrFail("slug")

                    if (slug == "uncategorized") {
                        val category = CategoryModel("uncategorized", null, "Okategoriserat", "uncategorized", "Bilder som inte tillhör en kategori")
                        val images = transaction {
                            ImageEntity.find {
                                (ImageTable.category eq null) and (ImageTable.id notInSubQuery ImageUploadTable.slice(ImageUploadTable.image).selectAll())
                            }.map(ImageEntity::toModel)
                        }
                        call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
                        return@get
                    }

                    val category = transaction {
                        CategoryEntity.find(CategoryTable.slug eq slug).firstOrNull() ?: CategoryEntity.findById(UUID.fromString(slug)) ?: throw Error("No such category.")
                    }
                    val images = transaction {
                        ImageEntity.find {
                            (ImageTable.category eq category.id) and (ImageTable.parent eq null) and (ImageTable.id notInSubQuery ImageUploadTable.slice(ImageUploadTable.image).selectAll())
                        }.map(ImageEntity::toModel)
                    }
                    call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
                }
            }
        }
    }
}