package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import se.ltu.student.models.Images.nullable
import java.util.*

class Upload(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Upload>(Uploads)

    var user by User optionalReferencedOn Uploads.user
    var images by Image via ImageUploads
}

object Uploads : UUIDTable() {
    val user = reference("user", Users).nullable()
}

// Pivot table
object ImageUploads : Table() {
    val upload = reference("upload", Uploads)
    val image = reference("image", Images)
    override val primaryKey = PrimaryKey(upload, image, name = "PK_UploadImages_upl_img")
}