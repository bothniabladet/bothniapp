package se.ltu.student.models

import com.drew.imaging.ImageMetadataReader
import kotlinx.serialization.serializer
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
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
    val photographer: PhotographerModel?,
    val imageSource: ImageSourceModel?,
    val metadata: ImageMetadata?,
    val variants: List<ImageModel>
)

// Workaround for recursive initializer violation
fun resolve(image: Image?): ImageModel? {
    return image?.toModel(false)
}

// Workaround for recursive initializer violation
fun resolve(parent: Image?, images: SizedIterable<Image>): List<ImageModel> {
    if (parent != null) {
        return listOf()
    }
    return images.map(Image::toModel)
}

// Workaround for recursive initializer violation
fun resolve(images: SizedIterable<Image>): List<ImageModel> {
    return images.map(Image::toModel)
}

// Find value for key in fields
fun valueFor(key: String, fields: Map<String, Map<String, String>>): String? {
    for (dir in fields)
        for (field in dir.value)
            if (field.key.contains(key))
                return field.value
    return null
}

class Image(id: EntityID<UUID>) : BaseUUIDEntity(id, Images) {
    companion object : BaseUUIDEntityClass<Image>(Images)

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
    var imageSource by ImageSource optionalReferencedOn Images.imageSource
    var photographer by Photographer optionalReferencedOn Images.photographer
    val variants by Image optionalReferrersOn Images.parent

    fun writeImage(bytes: ByteArray, extension: String, storagePath: String) {
        // Set the new filename
        this.path = "${this.id}.${extension}"

        // Persist file to disk
        val file = File("${storagePath}/$path")
        file.writeBytes(bytes)

        // Set metadata
        this.setMetadata(file)
    }

    private fun setMetadata(file: File) {
        val metadata: com.drew.metadata.Metadata = ImageMetadataReader.readMetadata(file)
        val fields = metadata.directories.associate { dir ->
            dir.name to dir.tags.filter { field ->
                !field.tagName.contains("Unknown")
            }.associate { field -> field.tagName to field.description }
        }

        valueFor("Image Width", fields)?.let { width -> this.width = width.replace("[^0-9]".toRegex(), "").toInt() }
        valueFor("Image Height", fields)?.let { height -> this.height = height.replace("[^0-9]".toRegex(), "").toInt() }

        this.metadata = ImageMetadata(fields)
    }

    fun deleteImage(storagePath: String) {
        this.variants.forEach { image -> image.deleteImage(storagePath) }
        File("$storagePath/$path").delete()
        super.delete()
    }

    override fun delete() {
        this.variants.forEach(Image::delete)
        super.delete()
    }

    fun toModel(loadChildren: Boolean = true) = ImageModel(id.toString(), resolve(parent), caption, description, path, size, width, height, category?.toModel(), photographer?.toModel(false), imageSource?.toModel(), metadata, if (loadChildren) resolve(parent, variants) else listOf())
}

@kotlinx.serialization.Serializable
data class ImageMetadata(val values: Map<String, Map<String, String>>)

object Images : BaseUUIDTable("images") {
    val parent = reference("parent", id, onDelete = ReferenceOption.CASCADE).nullable()
    val caption = varchar("caption", 256)
    val description = text("description").nullable()
    val path = varchar("path", 256).uniqueIndex().nullable()
    val size = integer("size")
    val width = integer("width").default(0)
    val height = integer("height").default(0)
    val category = reference("category", Categories, onDelete = ReferenceOption.SET_NULL).nullable()
    val imageSource = reference("source", ImageSources, onDelete = ReferenceOption.SET_NULL).nullable()
    val photographer = reference("photographer", Photographers, onDelete = ReferenceOption.SET_NULL).nullable()
    val metadata = jsonb<ImageMetadata>("metadata", serializer())
}