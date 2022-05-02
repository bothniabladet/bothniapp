package se.ltu.student.plugins

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

object DB {
    val connection by lazy {
        Database.connect("", "", "", "")
    }
}

fun Application.getDatabase(): Database {
    return DB.connection
}