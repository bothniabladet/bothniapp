package se.ltu.student.models.image

import com.drew.imaging.ImageMetadataReader
import org.jetbrains.exposed.dao.id.EntityID
import se.ltu.student.models.BaseEntity
import se.ltu.student.models.BaseEntityClass
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.imagesource.ImageSourceEntity
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.upload.ImageUploadTable
import se.ltu.student.models.upload.UploadEntity
import java.io.File
import java.util.*

class ImageEntity(id: EntityID<UUID>) : BaseEntity(id, ImageTable) {
    companion object : BaseEntityClass<ImageEntity>(ImageTable)

    var parent by ImageEntity optionalReferencedOn ImageTable.parent
    var caption by ImageTable.caption
    var description by ImageTable.description
    var path by ImageTable.path
    var size by ImageTable.size
    var width by ImageTable.width
    var height by ImageTable.height
    var category by CategoryEntity optionalReferencedOn ImageTable.category
    var metadata by ImageTable.metadata
    var upload by UploadEntity via ImageUploadTable
    var imageSource by ImageSourceEntity optionalReferencedOn ImageTable.imageSource
    var photographer by PhotographerEntity optionalReferencedOn ImageTable.photographer
    val variants by ImageEntity optionalReferrersOn ImageTable.parent

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
                !field.tagName.contains("Unknown") && field.hasTagName()
            }.associate { field -> field.tagName to handleNullString(field.description) }
        }

        valueFor("Image Width", fields)?.let { width -> this.width = width.replace("[^0-9]".toRegex(), "").toInt() }
        valueFor("Image Height", fields)?.let { height -> this.height = height.replace("[^0-9]".toRegex(), "").toInt() }

        this.metadata = ImageMetadata(fields)
    }

    fun deleteImage(storagePath: String) {
        this.variants.forEach { image -> image.deleteImage(storagePath) }
        File("$storagePath/$path").delete()
    }

    override fun delete() {
        this.variants.forEach(ImageEntity::delete)
        super.delete()
    }
}

// Find value for key in fields
fun valueFor(key: String, fields: Map<String, Map<String, String>>): String? {
    for (dir in fields)
        for (field in dir.value)
            if (field.key.contains(key))
                return field.value
    return null
}

fun handleNullString(string: String?): String {
    if (string == null)
        return ""
    return string
}