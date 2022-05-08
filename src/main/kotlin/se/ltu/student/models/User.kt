package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@kotlinx.serialization.Serializable
data class UserModel constructor(
    val givenName: String,
    val familyName: String,
    val email: String
)

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(Users)

    var givenName by Users.givenName
    var familyName by Users.familyName
    var email by Users.email
    var passwordHash by Users.passwordHash

    fun toModel() = UserModel(givenName, familyName, email)
}

object Users : UUIDTable() {
    val givenName = varchar("given_name", 128)
    val familyName = varchar("family_name", 128)
    val email = varchar("email", 256).uniqueIndex()
    val passwordHash = varchar("password_hash", 256)
}