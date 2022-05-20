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
import se.ltu.student.plugins.UserNotification

fun Route.createCategoryRoute() {
    route("new") {
        get {
            call.respondFMT(FreeMarkerContent("category/new.ftl", null))
        }

        post {
            val formParameters = call.receiveParameters()

            val name = formParameters.getOrFail("name")

            transaction {
                CategoryEntity.new {
                    this.name = name
                }
            }

            setVolatileNotification(UserNotification.success("Kategori tillagd."))

            call.respondRedirect("/category")
        }

    }
}