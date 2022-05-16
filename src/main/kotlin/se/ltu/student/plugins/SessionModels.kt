package se.ltu.student.plugins

import io.ktor.server.auth.*
import se.ltu.student.models.UserModel

// Session data classes
data class UserSession(val name: String, val model: UserModel) : Principal

data class UserNotification(val title: String?, val description: String, val type: String = "info") {
    companion object {
        fun success(text: String):UserNotification {
            return UserNotification(null, text, "success")
        }

        fun error(text: String):UserNotification {
            return UserNotification(null, text, "danger")
        }
    }
}

data class UserNotifications(val volatile: UserNotification? = null, val persistent: Map<String, UserNotification> = mapOf())