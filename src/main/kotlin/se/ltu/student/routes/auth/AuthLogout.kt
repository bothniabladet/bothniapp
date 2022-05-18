package se.ltu.student.routes.auth

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import se.ltu.student.plugins.UserSession

fun Route.logoutRoute() {
    get("/logout") {
        call.sessions.clear<UserSession>()
        call.respondRedirect("/login")
    }
}