package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.SizedIterable
import se.ltu.student.models.Photographer.Companion.backReferencedOn
import java.util.*

@kotlinx.serialization.Serializable
data class ImageSourceModel constructor(
    val id: String,
    val name: String,
    val website: String?,
    val images: List<ImageModel>
)

class ImageSource(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageSource>(ImageSources)

    var name by ImageSources.name
    var website by ImageSources.website
    val images by Image referrersOn ImageSources.id

    fun toModel(loadChildren: Boolean = false) = ImageSourceModel(id.toString(), name, website, if (loadChildren) resolve(images) else listOf())
}

object ImageSources : UUIDTable() {
    val name = varchar("name", 256)
    val website = varchar("website", 256).nullable()
}