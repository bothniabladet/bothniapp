package se.ltu.student.models

import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

@kotlinx.serialization.Serializable
data class UserModel constructor(
    val id: String,
    val givenName: String,
    val familyName: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)

class User(id: EntityID<UUID>) : BaseUUIDEntity(id, Users) {
    companion object : BaseUUIDEntityClass<User>(Users)

    var givenName by Users.givenName
    var familyName by Users.familyName
    var email by Users.email
    var passwordHash by Users.passwordHash

    fun toModel() = UserModel(id.toString(), givenName, familyName, email, createdAt.toString(), updatedAt.toString())
}

object Users : BaseUUIDTable("users") {
    val givenName = varchar("given_name", 128)
    val familyName = varchar("family_name", 128)
    val email = varchar("email", 256).uniqueIndex()
    val passwordHash = varchar("password_hash", 256)
}