package se.ltu.student.extensions

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.pipeline.*
import se.ltu.student.plugins.UserNotification
import se.ltu.student.plugins.UserNotifications

fun UserNotifications.with(volatile: UserNotification?): UserNotifications {
    return UserNotifications(volatile, this.persistent)
}

fun ApplicationCall.setVolatileNotification(userNotification: UserNotification) {
    val existing = sessions.get<UserNotifications>()

    if (existing == null)
        sessions.set(UserNotifications(userNotification))
    else
        sessions.set(existing.with(userNotification))
}

fun PipelineContext<Unit, ApplicationCall>.setVolatileNotification(userNotification: UserNotification) {
    call.setVolatileNotification(userNotification)
}

fun ApplicationCall.getAndDestroyVolatileNotificationIfPresent(): UserNotification? {
    val userNotifications = sessions.get<UserNotifications>() ?: return null

    if (userNotifications.volatile == null)
        return null

    sessions.set(userNotifications.with(null))

    return userNotifications.volatile
}