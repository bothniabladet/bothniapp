package se.ltu.student.models.upload

import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageModel
import se.ltu.student.models.image.toModel
import se.ltu.student.models.user.UserModel
import se.ltu.student.models.user.toModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@kotlinx.serialization.Serializable
data class UploadModel constructor(
    val id: String,
    val user: UserModel?,
    val images: List<ImageModel>,
    val created: String
)

fun UploadEntity.toModel() = UploadModel(id.toString(), user?.toModel(), images.map(ImageEntity::toModel), relativeDateTime(createdAt))

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