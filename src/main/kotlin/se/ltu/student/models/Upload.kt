package se.ltu.student.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

fun relativeDateTime(then: LocalDateTime): String {
    val now = LocalDateTime.now()
    val diff = now.toEpochSecond(ZoneOffset.UTC) - then.toEpochSecond(ZoneOffset.UTC)

    if (diff < 60)
        return "$diff sekunder sedan"

    if (diff < 60 * 60) {
        val minutes = diff / 60
        return "$minutes" + if (minutes > 1) " minuter sedan" else " minut sedan"
    }

    if (diff < 60 * 60 * 24) {
        val hours = diff / (60 * 60)
        return "$hours" + if (hours > 1) " timmar sedan" else " timme sedan"
    }

    if (diff < 60 * 60 * 24 * 28) {
        val hours = diff / (60 * 60 * 24)
        return "$hours" + if (hours > 1) " dagar sedan" else " dag sedan"
    }

    return then.format(DateTimeFormatter.BASIC_ISO_DATE)
}

@kotlinx.serialization.Serializable
data class UploadModel constructor(
    val id: String,
    val user: UserModel?,
    val images: List<ImageModel>,
    val created: String
)

class Upload(id: EntityID<UUID>) : BaseUUIDEntity(id, Uploads) {
    companion object : BaseUUIDEntityClass<Upload>(Uploads)

    var user by User optionalReferencedOn Uploads.user
    var images by Image via ImageUploads

    fun toModel() = UploadModel(id.toString(), user?.toModel(), images.map(Image::toModel), relativeDateTime(createdAt))
}

object Uploads : BaseUUIDTable("uploads") {
    val user = reference("user", Users).nullable()
}

// Pivot table
object ImageUploads : Table() {
    val upload = reference("upload", Uploads, onDelete = ReferenceOption.CASCADE)
    val image = reference("image", Images, onDelete = ReferenceOption.CASCADE)
    override val primaryKey = PrimaryKey(upload, image, name = "PK_UploadImages_upl_img")
}