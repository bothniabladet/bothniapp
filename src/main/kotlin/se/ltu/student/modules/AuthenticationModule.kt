package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import se.ltu.student.dao.dao
import se.ltu.student.plugins.UserSession

fun Application.configureModuleAuthentication() {
    // Login
    routing {
        get("/login") {
            val user = call.principal<UserSession>()
            if (user != null) {
                call.respondRedirect("/profile")
            } else {
                call.respond(FreeMarkerContent("authentication/login.ftl", null))
            }
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
                val user = call.principal<UserSession>()
                if (user != null) {
                    call.respondRedirect("/profile")
                } else {
                    call.respond(FreeMarkerContent("authentication/register.ftl", null))
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

                val user = dao.user.createUser(givenName, familyName, email, password)

                if (user != null) {
                    call.respondRedirect("/login?registered=true")
                } else {
                    call.respondRedirect("/register?registered=false")
                }
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