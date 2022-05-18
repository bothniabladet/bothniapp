package se.ltu.student.models.image

import kotlinx.serialization.serializer
import org.jetbrains.exposed.sql.ReferenceOption
import se.ltu.student.extensions.jsonb
import se.ltu.student.models.BaseTable
import se.ltu.student.models.category.CategoryTable
import se.ltu.student.models.imagesource.ImageSourceTable
import se.ltu.student.models.photographer.PhotographerTable

object ImageTable : BaseTable("images") {
    val parent = reference("parent", id, onDelete = ReferenceOption.CASCADE).nullable()
    val caption = varchar("caption", 256)
    val description = text("description").nullable()
    val path = varchar("path", 256).uniqueIndex().nullable()
    val size = integer("size")
    val width = integer("width").default(0)
    val height = integer("height").default(0)
    val category = reference("category", CategoryTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val imageSource = reference("source", ImageSourceTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val photographer = reference("photographer", PhotographerTable, onDelete = ReferenceOption.SET_NULL).nullable()
    val metadata = jsonb<ImageMetadata>("metadata", serializer())
}