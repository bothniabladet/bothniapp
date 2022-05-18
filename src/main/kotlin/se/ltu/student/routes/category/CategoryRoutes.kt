package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.categoryRoutes() {
    routing {
        listCategoriesRoute()
        createCategoryRoute()
        updateCategoryRoute()
        deleteCategoryRoute()
    }
}