package se.ltu.student

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import se.ltu.student.modules.*
import se.ltu.student.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", watchPaths = listOf("classes", "resources")) {
        configureDatabase()
        configureTemplating()
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureAuthentication()

        // Modules
        configureModuleAuthentication()
        configureModuleUser()
        configureModuleArchive()
        configureModuleSearch()
        configureModuleUpload()
    }.start(wait = true)
}
