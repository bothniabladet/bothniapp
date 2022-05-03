package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import se.ltu.student.plugins.UserSession

fun Application.configureModuleUser() {
    routing {
        authenticate("auth-session") {
            route("/profile") {
                get {
                    val userProfile = call.principal<UserSession>()?.userProfile
                    call.respond(FreeMarkerContent("profile/index.ftl", mapOf("userProfile" to userProfile)))
                }
                get("/edit") {
                    val userProfile = call.principal<UserSession>()?.userProfile
                    call.respond(FreeMarkerContent("profile/edit.ftl", mapOf("userProfile" to userProfile)))
                }
            }
        }
    }
}