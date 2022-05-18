package se.ltu.student.models.user

@kotlinx.serialization.Serializable
data class UserModel constructor(
    val id: String,
    val givenName: String,
    val familyName: String,
    val email: String,
    val createdAt: String,
    val updatedAt: String
)

fun UserEntity.toModel() =
    UserModel(id.toString(), givenName, familyName, email, createdAt.toString(), updatedAt.toString())