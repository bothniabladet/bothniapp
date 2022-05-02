package se.ltu.student.plugins

import io.ktor.server.application.*
import io.ktor.server.freemarker.*

fun Application.configureTemplating() {
    install(FreeMarker)
}