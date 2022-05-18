package se.ltu.student.models.user

import se.ltu.student.models.BaseTable

object UserTable : BaseTable("users") {
    val givenName = varchar("given_name", 128)
    val familyName = varchar("family_name", 128)
    val email = varchar("email", 256).uniqueIndex()
    val passwordHash = varchar("password_hash", 256)
}