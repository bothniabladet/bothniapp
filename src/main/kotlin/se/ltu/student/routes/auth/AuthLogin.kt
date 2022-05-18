package se.ltu.student.routes.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import se.ltu.student.extensions.respondFMT
import se.ltu.student.plugins.UserSession
import se.ltu.student.routes.redirectIfPossible

fun Route.loginRoute() {
    route("/login") {
        get {
            if (call.sessions.get<UserSession>() != null)
                return@get call.respondRedirect("/profile")

            call.respondFMT(FreeMarkerContent("authentication/login.ftl", null))
        }

        authenticate("auth-form") {
            post {
                call.sessions.set(call.principal<UserSession>())

                if (!redirectIfPossible())
                    call.respondRedirect("/")
            }
        }
    }
}