package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.Image
import se.ltu.student.models.Images

fun Application.configureModuleSearch() {
    routing {
        route("/search") {
            get {
                call.respondFMT(FreeMarkerContent("search.ftl", mapOf("query" to call.parameters["query"])))
            }

            post {
                val query = call.parameters["query"] ?: ""

                val images = transaction {
                    Images.caption like "%query%"
                }
                call.respondFMT(FreeMarkerContent("search.ftl", mapOf("query" to query, "images" to images)))
            }
        }
    }
}