package se.ltu.student.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import se.ltu.student.models.UserModel

// Session data classes
data class UserSession(val name: String, val model: UserModel) : Principal

data class OperationStatusMessage(val type: String, val message: String)

fun Application.configureSecurity() {
    val secretEncryptKey = hex(environment.config.propertyOrNull("ktor.security.secretEncryptKey")?.getString() ?: "00112233445566778899aabbccddeeff")
    val secretSignKey = hex(environment.config.propertyOrNull("ktor.security.secretSignKey")?.getString() ?: "6819b57a326945c1968f45236589")

    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60 * 60 * 48
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }

        cookie<OperationStatusMessage>("message", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }
}
