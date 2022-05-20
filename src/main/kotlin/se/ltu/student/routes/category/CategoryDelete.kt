package se.ltu.student.routes.category

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.setVolatileNotification
import se.ltu.student.models.category.CategoryEntity
import se.ltu.student.plugins.UserNotification
import se.ltu.student.routes.getIdOrFail
import se.ltu.student.routes.redirectIfPossible

fun Route.deleteCategoryRoute() {
    post("/delete") {
        val id = getIdOrFail()

        transaction {
            val category = CategoryEntity.findById(id) ?: throw Error("No such category.")
            category.delete()
        }

        setVolatileNotification(UserNotification.success("Kategori raderad."))

        if (!redirectIfPossible())
            call.respondRedirect("/category")
    }
}