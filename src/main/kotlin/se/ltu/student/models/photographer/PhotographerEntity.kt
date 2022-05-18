package se.ltu.student.models.photographer

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable
import se.ltu.student.models.imagesource.ImageSourceEntity
import java.util.*

class PhotographerEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<PhotographerEntity>(PhotographerTable)

    var givenName by PhotographerTable.givenName
    var familyName by PhotographerTable.familyName
    var email by PhotographerTable.email
    var phone by PhotographerTable.phone
    var imageSource by ImageSourceEntity optionalReferencedOn PhotographerTable.imageSource
    val images by ImageEntity optionalReferrersOn ImageTable.photographer
}