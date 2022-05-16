package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.models.User.Companion.referrersOn
import java.util.*

@kotlinx.serialization.Serializable
data class PhotographerModel constructor(
    val id: String,
    val givenName: String,
    val familyName: String,
    val email: String?,
    val phone: String?,
    val imageSource: ImageSourceModel?,
    val images: List<ImageModel>
)

class Photographer(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Photographer>(Photographers)

    var givenName by Photographers.givenName
    var familyName by Photographers.familyName
    var email by Photographers.email
    var phone by Photographers.phone
    var imageSource by ImageSource optionalReferencedOn Photographers.imageSource
    val images by Image optionalReferrersOn Images.photographer

    fun toModel(loadChildren: Boolean = false) = PhotographerModel(id.toString(), givenName, familyName, email, phone, imageSource?.toModel(), if (loadChildren) resolve(images).filter { image -> image.published && image.parent == null } else listOf())
}

object Photographers : UUIDTable() {
    val givenName = varchar("given_name", 256)
    val familyName = varchar("family_name", 256)
    val email = varchar("email", 256).nullable()
    val phone = varchar("phone", 20).nullable()
    val imageSource = reference("source", ImageSources, onDelete = ReferenceOption.SET_NULL).nullable()
}