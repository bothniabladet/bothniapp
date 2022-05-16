package se.ltu.student.plugins

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*

import io.ktor.server.response.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.User
import se.ltu.student.models.UserModel
import se.ltu.student.models.Users
import java.util.UUID

fun verifyPassword(password: String, passwordHash: CharArray): Boolean {
    return BCrypt.verifyer().verify(password.toCharArray(), passwordHash).verified
}

fun Application.configureAuthentication() {
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"

            // Set redirect for if auth fails
            this.challenge("/login")

            validate { credentials ->
                val user = transaction {
                    User.find { Users.email eq credentials.name }.firstOrNull()
                }

                if (user == null) {
                    setVolatileNotification(UserNotification.error("Ogiltigt användarnamn eller lösenord."))
                    return@validate null
                }

                if (!verifyPassword(credentials.password, user.passwordHash.toCharArray())) {
                    setVolatileNotification(UserNotification.error("Ogiltigt användarnamn eller lösenord."))
                    return@validate null
                }

                val model = transaction { user.toModel() }
                UserSession(credentials.name, model)
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
