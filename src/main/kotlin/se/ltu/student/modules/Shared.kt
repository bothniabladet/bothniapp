package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.util.*
import io.ktor.util.pipeline.*
import java.util.*

// Pipeline context

fun PipelineContext<Unit, ApplicationCall>.getIdOrFail(): UUID {
    return UUID.fromString(this.call.parameters.getOrFail("id")) ?: throw Error("Invalid UUID")
}

suspend fun PipelineContext<Unit, ApplicationCall>.redirectIfPossible(): Boolean {
    val redirect = this.call.parameters["redirect"]

    if (redirect != null)
        this.call.respondRedirect(redirect)

    return redirect != null
}