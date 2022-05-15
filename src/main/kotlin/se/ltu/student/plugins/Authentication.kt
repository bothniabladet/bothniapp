package se.ltu.student.plugins

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*

import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.User
import se.ltu.student.models.UserModel
import se.ltu.student.models.Users
import java.util.UUID

fun verifyPassword(password: String, passwordHash: CharArray): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified
}

data class UserSession(val name: String, val model: UserModel) : Principal

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
                    val model = transaction { user.toModel() }
                    UserSession(credentials.name, model)
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
                call.respondRedirect("/login?redirect=${this.call.request.path()}")
            }
        }
    }
}
