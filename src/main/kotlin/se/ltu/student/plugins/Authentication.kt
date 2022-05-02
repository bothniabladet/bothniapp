package se.ltu.student.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

import io.ktor.server.response.*
import se.ltu.student.dao.dao
import se.ltu.student.models.User

data class UserSession(val name: String, val user: User) : Principal

fun Application.configureAuthentication() {
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                val user = dao.user.authenticate(credentials.name, credentials.password)

                if (user != null) {
                    UserSession(credentials.name, user)
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
