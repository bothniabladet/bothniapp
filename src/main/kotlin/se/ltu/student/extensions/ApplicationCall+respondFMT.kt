package se.ltu.student.extensions

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import se.ltu.student.plugins.UserSession

fun FreeMarkerContent.expand(key: String, model: Any?): FreeMarkerContent {
    val _model: MutableMap<Any?, Any?> = mutableMapOf()

    val tmodel = this.model // template model
    if (tmodel is Map<*, *>) {
        for ((key, value) in tmodel) {
            _model[key] = value
        }
    }
    _model[key] = model

    return FreeMarkerContent(this.template, _model.toMap(), this.etag, this.contentType)
}

@OptIn(InternalAPI::class)
@JvmName("respondWithType")
public suspend inline fun ApplicationCall.respondFMT(content: FreeMarkerContent) {
    val session = sessions.get<UserSession>()
    val newContent = content.expand("state", mapOf("isAuthenticated" to (session != null)))
    respond(newContent)
}