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
                    val user = call.principal<UserSession>()?.user
                    call.respond(FreeMarkerContent("profile/index.ftl", mapOf("user" to user)))
                }
                get("/edit") {
                    val user = call.principal<UserSession>()?.user
                    call.respond(FreeMarkerContent("profile/edit.ftl", mapOf("user" to user)))
                }
            }
        }
    }
}