package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.categoryRoutes() {
    routing {
        authenticate("auth-session") {
            route("/category") {
                listCategoriesRoute()
                createCategoryRoute()
                categoryBySlugRoute()

                route("/{id}") {
                    updateCategoryRoute()
                    deleteCategoryRoute()
                }
            }
        }
    }
}