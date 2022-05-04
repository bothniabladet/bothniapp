package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import se.ltu.student.models.Images.nullable
import java.util.*

class Upload(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Upload>(Uploads)

    var user by User optionalReferencedOn Uploads.user
}

object Uploads : UUIDTable() {
    val user = reference("user", Users).nullable()
}

class ImageUpload(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageUpload>(ImageUploads)

    var upload by Upload referencedOn ImageUploads.upload
    var image by Image referencedOn ImageUploads.image
}

// Pivot table
object ImageUploads : UUIDTable() {
    val upload = reference("upload", Uploads)
    val image = reference("image", Images)
    // TODO: val user = reference("user", Users).nullable()
}