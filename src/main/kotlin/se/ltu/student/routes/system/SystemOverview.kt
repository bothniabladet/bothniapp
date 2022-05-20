package se.ltu.student.routes.system

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import se.ltu.student.extensions.respondFMT

fun Route.systemOverviewRoute() {
    get {
        call.respondFMT(FreeMarkerContent("system/index.ftl", null))
    }
}