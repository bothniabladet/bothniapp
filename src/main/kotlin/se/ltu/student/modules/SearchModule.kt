package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.image.ImageEntity
import se.ltu.student.models.image.ImageTable

@kotlinx.serialization.Serializable
data class SearchResultItem(val title: String)

fun ImageEntity.toSearchResultItem(): SearchResultItem {
    return SearchResultItem(this.caption)
}

fun Application.configureModuleSearch() {
    routing {
        route("/search") {
            get {
                call.respondFMT(FreeMarkerContent("search.ftl", mapOf("query" to call.parameters["query"])))
            }

            post {
                val query = call.parameters["query"] ?: ""

                val images = transaction {
                    ImageEntity.find {
                        ImageTable.caption like "%${query}%"
                    }.map(ImageEntity::toSearchResultItem)
                }
                call.respond(images)
            }
        }
    }
}