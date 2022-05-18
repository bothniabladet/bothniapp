package se.ltu.student.models.upload

import org.jetbrains.exposed.dao.id.EntityID
import se.ltu.student.models.BaseEntity
import se.ltu.student.models.BaseEntityClass
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.user.UserEntity
import java.util.*

class UploadEntity(id: EntityID<UUID>) : BaseEntity(id, UploadTable) {
    companion object : BaseEntityClass<UploadEntity>(UploadTable)

    var user by UserEntity optionalReferencedOn UploadTable.user
    var images by ImageEntity via ImageUploadTable
}