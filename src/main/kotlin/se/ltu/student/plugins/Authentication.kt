package se.ltu.student.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*

import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
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

    // Login
    routing {
        get("/login") {
            call.respond(FreeMarkerContent("auth/login.ftl", null))
        }

        authenticate("auth-form") {
            post("/login") {
                call.sessions.set(call.principal<UserSession>())
                call.respondRedirect("/")
            }
        }
    }

    // Register
    routing {

        route("/register") {
            get {
                call.respond(FreeMarkerContent("auth/register.ftl", null))
            }

            post {
                val formParameters = call.receiveParameters()

                val email = formParameters.getOrFail("email")
                val repeatEmail = formParameters.getOrFail("repeatEmail")

                if (!repeatEmail.contentEquals(email)) {
                    call.respond("Email does not match.")
                }

                val password = formParameters.getOrFail("password")
                val repeatPassword = formParameters.getOrFail("repeatPassword")

                if (!repeatPassword.contentEquals(password)) {
                    call.respond("Password does not match.")
                }

                val givenName = formParameters.getOrFail("givenName")
                val familyName = formParameters.getOrFail("familyName")

                val user = dao.user.createUser(givenName, familyName, email, password)

                call.respondRedirect("/login?registered=true")
            }
        }
    }

    // Logout
    routing {
        get("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }
    }
}
