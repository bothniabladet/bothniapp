package se.ltu.student.models

import com.drew.imaging.ImageMetadataReader
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
    val variants by Image optionalReferrersOn Images.parent

    fun writeImage(bytes: ByteArray, extension: String) {
        // Set the new filename
        this.path = "${this.id}.${extension}"

        // Persist file to disk
        val file = File("uploads/$path")
        file.writeBytes(bytes)

        // Set metadata
        this.setMetadata(file)
    }

    private fun setMetadata(file: File) {
        val metadata: com.drew.metadata.Metadata = ImageMetadataReader.readMetadata(file)
        val tags = metadata.directories.associate { dir ->
            dir.name to dir.tags.filter { tag ->
                !tag.tagName.contains("Unknown")
            }.associate { tag -> tag.tagName to tag.description }
        }

        for (dir in tags) {
            for (tag in dir.value) {
                if (tag.key.contains("Image Width")) {
                    this.width = tag.value.toInt()
                }
                if (tag.key.contains("Image Height")) {
                    this.height = tag.value.toInt()
                }
                if (this.width > 0 && this.height > 0)
                    break
            }
            if (this.width > 0 && this.height > 0)
                break
        }

        this.metadata = ImageMetadata(tags)
    }

    override fun delete() {
        File("uploads/$path").delete()
        super.delete()
    }

    fun toModel(loadChildren: Boolean = true) = ImageModel(id.toString(), resolve(parent), caption, description, path, size, width, height, category?.toModel(), metadata,  if (loadChildren) resolve(parent, variants) else listOf())
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
    val metadata = jsonb<ImageMetadata>("metadata", serializer())
}