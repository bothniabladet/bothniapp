package se.ltu.student.dao

import at.favre.lib.crypto.bcrypt.BCrypt
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import se.ltu.student.dao.DatabaseFactory.dbQuery
import se.ltu.student.models.User
import se.ltu.student.models.Users
import java.util.UUID

class DAOUser {
    private fun resultRowToUser(resultRow: ResultRow) = User(
        id = resultRow[Users.id],
        givenName = resultRow[Users.givenName],
        familyName = resultRow[Users.familyName],
        email = resultRow[Users.email],
        null
    )

    suspend fun user(id: UUID): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    suspend fun createUser(givenName: String, familyName: String, email: String, password: String): User? = dbQuery {
        val insertStatement = Users.insert {
            it[Users.givenName] = givenName
            it[Users.familyName] = familyName
            it[Users.email] = email
            it[passwordHash] = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    /**
     * Attempt to authenticate user.
     * @return User if authenticated, otherwise null.
     */
    suspend fun authenticate(email: String, password: String): User? = dbQuery {
        val result = Users.select { Users.email eq email }
        val passwordHash = result.map { it[Users.passwordHash] }.singleOrNull()
        val user = result.map(::resultRowToUser).singleOrNull()

        println(result)
        println(user)
        println(passwordHash)

        when (!passwordHash.isNullOrEmpty() && BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified) {
            true -> user
            else -> null
        }
    }

}

