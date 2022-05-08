package se.ltu.student.models

import kotlinx.serialization.serializer
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.extensions.jsonb
import java.io.File
import java.util.*

@kotlinx.serialization.Serializable
data class ImageModel constructor(
    val id: String,
    val parent: ImageModel?,
    val caption: String,
    val description: String?,
    val path: String?,
    val size: Int,
    val width: Int,
    val height: Int,
    val category: CategoryModel?,
    val metadata: ImageMetadata?,
    val variations: List<ImageModel>
)

// Workaround for recursive initializer violation
fun resolve(image: Image?): ImageModel? {
    return image?.toModel()
}

// Workaround for recursive initializer violation
fun resolve(images: SizedIterable<Image>): List<ImageModel> {
    return images.map(Image::toModel)
}

class Image(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Image>(Images)

    var parent by Image optionalReferencedOn Images.parent
    var caption by Images.caption
    var description by Images.description
    var path by Images.path
    var size by Images.size
    var width by Images.width
    var height by Images.height
    var category by Category optionalReferencedOn Images.category
    var metadata by Images.metadata
    var upload by Upload via ImageUploads
    val variations by Image optionalReferrersOn Images.parent

    override fun delete() {
        File("uploads/$path").delete()
        super.delete()
    }

    fun toModel() = ImageModel(id.toString(), resolve(parent), caption, description, path, size, width, height, category?.toModel(), metadata, resolve(variations))
}

@kotlinx.serialization.Serializable
data class ImageMetadata(val values: Map<String, Map<String, String>>)

object Images : UUIDTable() {
    val parent = reference("parent", id).nullable()
    val caption = varchar("caption", 256)
    val description = text("description").nullable()
    val path = varchar("path", 256).uniqueIndex().nullable()
    val size = integer("size")
    val width = integer("width").default(0)
    val height = integer("height").default(0)
    val category = reference("category", Categories).nullable()
    val metadata = jsonb<ImageMetadata>("metadata", serializer()).nullable()
}