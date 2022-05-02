package se.ltu.student.plugins

import io.ktor.server.application.*
import se.ltu.student.dao.DatabaseFactory

fun Application.configureDatabase() {
    DatabaseFactory.init()
}