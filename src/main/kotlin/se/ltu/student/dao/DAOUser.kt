package se.ltu.student.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import se.ltu.student.dao.DatabaseFactory.dbQuery
import se.ltu.student.models.User
import se.ltu.student.models.Users

class DAOUser {
    private fun resultRowToUser(resultRow: ResultRow) = User(
        id = resultRow[Users.id],
        givenName = resultRow[Users.givenName],
        familyName = resultRow[Users.familyName],
        email = resultRow[Users.email],
        passwordHash = resultRow[Users.passwordHash]
    )

    suspend fun user(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }
}

