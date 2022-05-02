package se.ltu.student

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import se.ltu.student.modules.moduleHealthCheck
import se.ltu.student.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureTemplating()
        configureRouting()
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureAuthentication()

        moduleHealthCheck()
    }.start(wait = true)
}
