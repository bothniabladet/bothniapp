package se.ltu.student.plugins

import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import se.ltu.student.extensions.respondFMT
import se.ltu.student.routes.archive.archiveRoutes
import se.ltu.student.routes.auth.authRoutes
import se.ltu.student.routes.category.categoryRoutes
import se.ltu.student.routes.image.imageRoutes
import se.ltu.student.routes.imagesource.imageSourceRoutes
import se.ltu.student.routes.photographer.photographerRoutes
import se.ltu.student.routes.search.searchRoutes
import se.ltu.student.routes.upload.uploadRoutes
import se.ltu.student.routes.user.userRoutes

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

    archiveRoutes()
    authRoutes()
    categoryRoutes()
    imageRoutes()
    imageSourceRoutes()
    photographerRoutes()
    searchRoutes()
    uploadRoutes()
    userRoutes()
}

class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
