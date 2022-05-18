package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.models.category.toModel

fun Route.listCategoriesRoute() {
    get {
        val categories = transaction {
            CategoryEntity.all().map(CategoryEntity::toModel)
        }
        call.respondFMT(FreeMarkerContent("config/category/index.ftl", mapOf("categories" to categories)))
    }
}