package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.CategoryModel
import se.ltu.student.models.category.CategoryTable
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable
import se.ltu.student.models.image.toModel
import se.ltu.student.models.upload.ImageUploadTable
import se.ltu.student.routes.getSlugOrFail
import java.util.*

fun Route.categoryBySlugRoute() {
    get("/{slug}") {
        val slug = getSlugOrFail()

        if (slug == "uncategorized") {
            val category = CategoryModel("uncategorized", null, "Okategoriserat", "uncategorized", "Bilder som inte tillhör en kategori")

            val images = transaction {
                ImageEntity.find {
                    (ImageTable.category eq null) and (ImageTable.id notInSubQuery ImageUploadTable.slice(
                        ImageUploadTable.image).selectAll())
                }.map(ImageEntity::toModel)
            }

            return@get call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
        }

        val category = transaction {
            CategoryEntity.find(CategoryTable.slug eq slug).firstOrNull() ?: CategoryEntity.findById(UUID.fromString(slug)) ?: throw Error("No such category.")
        }

        val images = transaction {
            ImageEntity.find {
                (ImageTable.category eq category.id) and (ImageTable.parent eq null) and (ImageTable.id notInSubQuery ImageUploadTable.slice(
                    ImageUploadTable.image).selectAll())
            }.map(ImageEntity::toModel)
        }

        call.respondFMT(FreeMarkerContent("archive/category.ftl", mapOf("category" to category, "images" to images)))
    }
}