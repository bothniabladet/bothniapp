package se.ltu.student.routes.search

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable

@kotlinx.serialization.Serializable
data class SearchResultItem(val title: String, val link: String)

fun Route.searchByQuery() {
    get {
        call.respondFMT(FreeMarkerContent("search.ftl", mapOf("query" to call.parameters["query"])))
    }

    post {
        val query = call.parameters.getOrFail("query")

        val images = transaction {
            ImageEntity.find {
                ImageTable.caption like "%${query}%"
            }.map { image ->
                SearchResultItem(image.caption, "/image/${image.id}")
            }
        }

        call.respond(images)
    }
}