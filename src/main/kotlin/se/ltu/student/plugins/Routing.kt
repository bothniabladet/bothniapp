package se.ltu.student.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import se.ltu.student.extensions.respondFMT
import se.ltu.student.modules.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondFMT(FreeMarkerContent("index.ftl", null))
        }

        route("api") {
            // API routes
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }

    configureModuleAuthentication()
    configureModuleUser()
    configureModuleArchive()
    configureModuleSearch()
    configureModuleUpload()
    configureModuleConfig()
    configureModulePhotographer()
    configureModuleImageSource()
    configureModuleImage()
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
