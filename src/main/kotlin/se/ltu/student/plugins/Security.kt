package se.ltu.student.plugins

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSecurity() {
    val secretEncryptKey = hex(environment.config.propertyOrNull("ktor.security.secretEncryptKey")?.getString() ?: "00112233445566778899aabbccddeeff")
    val secretSignKey = hex(environment.config.propertyOrNull("ktor.security.secretSignKey")?.getString() ?: "6819b57a326945c1968f45236589")

    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 5 * 60
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }
}
