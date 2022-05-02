package se.ltu.student.models

import org.jetbrains.exposed.sql.*
import java.util.*

data class User(val id: UUID, val givenName: String, val familyName: String, val email: String, val passwordHash: String?)

object Users : Table() {
    val id = uuid("id").autoGenerate()
    val givenName = varchar("given_name", 128)
    val familyName = varchar("family_name", 128)
    val email = varchar("email", 256).uniqueIndex()
    val passwordHash = varchar("password_hash", 256)

    override val primaryKey = PrimaryKey(id)
}