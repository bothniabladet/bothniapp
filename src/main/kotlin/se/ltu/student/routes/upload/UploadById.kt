package se.ltu.student.routes.upload

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.imagesource.toModel
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.toModel
import se.ltu.student.models.upload.UploadEntity
import se.ltu.student.models.upload.toModel
import se.ltu.student.routes.getIdOrFail

fun Route.uploadByIdRoute() {
    get {
        val id = getIdOrFail()

        val upload = transaction {
            UploadEntity.findById(id)?.toModel()
        }

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
                "upload/manage.ftl",
                mapOf(
                    "upload" to upload,
                    "categories" to categories,
                    "photographers" to photographers,
                    "imageSources" to imageSources
                )
            )
        )
    }
}