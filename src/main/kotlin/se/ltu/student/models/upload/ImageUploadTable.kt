package se.ltu.student.models.upload

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import se.ltu.student.models.image.ImageTable

object ImageUploadTable : Table("imageuploads") {
    val upload = reference("upload", UploadTable, onDelete = ReferenceOption.CASCADE)
    val image = reference("image", ImageTable, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(upload, image, name = "PK_UploadImages_upl_img")
}