package se.ltu.student.routes

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import io.ktor.util.pipeline.*
import se.ltu.student.models.user.UserModel
import se.ltu.student.plugins.UserSession
import java.util.*

// UUID from String

fun String.parseUUIDOrFail(): UUID {
    return UUID.fromString(this) ?: throw Error("Invalid UUID")
}

// Pipeline context

fun PipelineContext<Unit, ApplicationCall>.getSlugOrFail(): String {
    return call.parameters.getOrFail("slug")
}

fun PipelineContext<Unit, ApplicationCall>.getIdOrFail(): UUID {
    return call.parameters.getOrFail("id").parseUUIDOrFail()
}

fun PipelineContext<Unit, ApplicationCall>.getAuthenticatedUserOrFail(): UserModel {
    return call.principal<UserSession>()?.model ?: throw Error("Failed to get authenticated user.")
}

fun PipelineContext<Unit, ApplicationCall>.getAuthenticatedUserIdOrFail(): UUID {
    return getAuthenticatedUserOrFail().id.parseUUIDOrFail()
}

suspend fun PipelineContext<Unit, ApplicationCall>.redirectIfPossible(): Boolean {
    val redirect = call.parameters["redirect"]

    if (redirect != null)
        call.respondRedirect(redirect)

    return redirect != null
}