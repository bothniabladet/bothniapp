package se.ltu.student.models.imagesource

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable
import se.ltu.student.models.photographer.PhotographerEntity
import se.ltu.student.models.photographer.PhotographerTable
import java.util.*

class ImageSourceEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<ImageSourceEntity>(ImageSourceTable)

    var name by ImageSourceTable.name
    var website by ImageSourceTable.website
    val images by ImageEntity optionalReferrersOn ImageTable.imageSource
    val photographers by PhotographerEntity optionalReferrersOn PhotographerTable.imageSource
}
