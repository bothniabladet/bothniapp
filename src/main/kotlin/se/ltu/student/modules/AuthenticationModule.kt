package se.ltu.student.modules

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.User
import se.ltu.student.plugins.UserNotification
import se.ltu.student.plugins.UserSession

fun hashPassword(password: String): String {
    return BCrypt.withDefaults().hashToString(12, password.toCharArray())
}

fun Application.configureModuleAuthentication() {
    // Login
    routing {
        get("/login") {
            if (call.sessions.get<UserSession>() != null) {
                call.respondRedirect("/profile")
            } else {
                call.respondFMT(FreeMarkerContent("authentication/login.ftl", null))
            }
        }

        authenticate("auth-form") {
            post("/login") {
                call.sessions.set(call.principal<UserSession>())

                val redirect = call.parameters["redirect"]
                if (redirect != null)
                    call.respondRedirect(redirect)
                else
                    call.respondRedirect("/")
            }
        }
    }

    // Register
    routing {
        route("/register") {
            get {
                if (call.sessions.get<UserSession>() != null) {
                    call.respondRedirect("/profile")
                } else {
                    call.respondFMT(FreeMarkerContent("authentication/register.ftl", null))
                }
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

                val passwordHash = hashPassword(password)

                transaction {
                    User.new {
                        this.givenName = givenName
                        this.familyName = familyName
                        this.email = email
                        this.passwordHash = passwordHash
                    }
                }

                call.setVolatileNotification(UserNotification.success("Konto skapat! Du kan nu logga in."))

                call.respondRedirect("/login")
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