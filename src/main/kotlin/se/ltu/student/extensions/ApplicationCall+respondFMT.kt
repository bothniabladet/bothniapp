package se.ltu.student.extensions

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import se.ltu.student.plugins.UserSession

fun FreeMarkerContent.expand(injectedItems: Map<String, Any?>): FreeMarkerContent {
    val mutableMap: MutableMap<Any?, Any?> = mutableMapOf()

    val tmodel = this.model // template model
    if (tmodel is Map<*, *>) {
        for ((key, value) in tmodel) {
            mutableMap[key] = value
        }
    }
    for ((key, value) in injectedItems) {
        mutableMap[key] = value
    }
    return FreeMarkerContent(this.template, mutableMap.toMap(), this.etag, this.contentType)
}

@JvmName("respondWithType")
public suspend inline fun ApplicationCall.respondFMT(content: FreeMarkerContent) {
    val session = sessions.get<UserSession>()
    val volatileNotification = getAndDestroyVolatileNotificationIfPresent()
    val newContent = content.expand(mapOf("state" to mapOf("isAuthenticated" to (session != null), "volatileNotification" to volatileNotification)))
    respond(newContent)
}