package se.ltu.student.models.user

import org.jetbrains.exposed.dao.id.EntityID
import se.ltu.student.models.BaseEntity
import se.ltu.student.models.BaseEntityClass
import java.util.*

class UserEntity(id: EntityID<UUID>) : BaseEntity(id, UserTable) {
    companion object : BaseEntityClass<UserEntity>(UserTable)

    var givenName by UserTable.givenName
    var familyName by UserTable.familyName
    var email by UserTable.email
    var passwordHash by UserTable.passwordHash
}