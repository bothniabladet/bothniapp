package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail

fun Route.updateCategoryRoute() {
    route("/edit") {
        get {
            val id = getIdOrFail()

            val category = transaction {
                CategoryEntity.findById(id)?.toModel() ?: throw Error("No such category.")
            }
            call.respondFMT(FreeMarkerContent("config/category/edit.ftl", mapOf("category" to category)))
        }

        post {
            val id = getIdOrFail()

            val formParameters = call.receiveParameters()

            val name = formParameters.getOrFail("name")
            val description = formParameters.getOrFail("description")
            val slug = formParameters.getOrFail("slug")

            transaction {
                val category = CategoryEntity.findById(id) ?: throw Error("No such category.")
                category.name = name
                category.description = description
                category.slug = slug
            }

            setVolatileNotification(UserNotification.success("Ändringar sparade."))

            call.respondRedirect("/category")
        }
    }
}