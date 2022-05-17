package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.models.Photographer.Companion.backReferencedOn
import se.ltu.student.models.Photographer.Companion.optionalReferrersOn
import java.util.*

@kotlinx.serialization.Serializable
data class ImageSourceModel constructor(
    val id: String,
    val name: String,
    val website: String?,
    val photographers: List<PhotographerModel>,
    val images: List<ImageModel>
)

// Workaround for recursive initializer violation
fun resolve(photographers: SizedIterable<Photographer>): List<PhotographerModel> {
    return photographers.map(Photographer::toModel)
}

class ImageSource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageSource>(ImageSources)

    var name by ImageSources.name
    var website by ImageSources.website
    val images by Image optionalReferrersOn Images.imageSource
    val photographers by Photographer optionalReferrersOn Photographers.imageSource

    fun toModel(loadChildren: Boolean = false) = ImageSourceModel(id.toString(), name, website, if (loadChildren) resolve(photographers) else listOf(), if (loadChildren) resolve(images) else listOf())
}

object ImageSources : UUIDTable() {
    val name = varchar("name", 256)
    val website = varchar("website", 256).nullable()
}