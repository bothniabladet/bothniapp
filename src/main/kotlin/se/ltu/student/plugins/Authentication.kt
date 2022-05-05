package se.ltu.student.plugins

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.*
import io.ktor.server.auth.*

import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.User
import se.ltu.student.models.Users
import java.util.UUID

fun verifyPassword(password: String, passwordHash: CharArray): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified
}

data class UserProfile(val id: UUID, val givenName: String, val familyName: String, val email: String)

data class UserSession(val name: String, val userProfile: UserProfile) : Principal

fun Application.configureAuthentication() {
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                val user: User = transaction {
                    val id = Users.select { Users.email eq credentials.name }.map { it[Users.id] }.firstOrNull()
                    if (id != null) {
                        User.findById(id)
                    } else {
                        null
                    }
                } ?: throw Error("Incorrect username or password.")

                if (verifyPassword(credentials.password, user.passwordHash.toCharArray())) {
                    UserSession(credentials.name, UserProfile(user.id.value, user.givenName, user.familyName, user.email))
                } else {
                    null
                }
            }
        }

        session<UserSession>("auth-session") {
            validate { session ->
                session
            }
            challenge {
                call.respondRedirect("/login")
            }
        }
    }
}
