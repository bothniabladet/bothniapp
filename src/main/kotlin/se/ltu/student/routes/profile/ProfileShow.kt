package se.ltu.student.routes.profile

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import se.ltu.student.extensions.respondFMT
import se.ltu.student.routes.getAuthenticatedUserOrFail

fun Route.showProfileRoute() {
    get {
        val user = getAuthenticatedUserOrFail()

        call.respondFMT(FreeMarkerContent("profile/index.ftl", mapOf("user" to user)))
    }
}
