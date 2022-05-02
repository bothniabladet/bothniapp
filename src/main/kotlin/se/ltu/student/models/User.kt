package se.ltu.student.models

import org.jetbrains.exposed.sql.*

data class User(val id: Int, val givenName: String, val familyName: String, val email: String, val passwordHash: String)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val givenName = varchar("given_name", 128)
    val familyName = varchar("family_name", 128)
    val email = varchar("email", 256)
    val passwordHash = varchar("password_hash", 256)

    override val primaryKey = PrimaryKey(id)
}