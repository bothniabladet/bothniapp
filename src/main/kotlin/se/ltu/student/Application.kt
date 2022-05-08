package se.ltu.student

import io.ktor.server.application.*
import se.ltu.student.modules.*
import se.ltu.student.plugins.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
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
    configureModuleConfig()
}
