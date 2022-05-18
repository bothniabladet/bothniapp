package se.ltu.student.modules

import io.ktor.server.application.*
import io.ktor.server.auth.*
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
import java.util.*

fun Application.configureModuleConfig() {
    routing {
        authenticate("auth-session") {
            route("/config") {
                get {
                    call.respondFMT(FreeMarkerContent("config/index.ftl", null))
                }

                route("/category") {
                    get {
                        val categories = transaction {
                            CategoryEntity.all().map(CategoryEntity::toModel)
                        }
                        call.respondFMT(FreeMarkerContent("config/category/index.ftl", mapOf("categories" to categories)))
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

                        call.respondRedirect("/config/category")
                    }

                    route("/{id}") {
                        get {
                            val id = UUID.fromString(call.parameters.getOrFail("id"))

                            val category = transaction {
                                CategoryEntity.findById(id)?.toModel() ?: throw Error("No such category.")
                            }
                            call.respondFMT(FreeMarkerContent("config/category/edit.ftl", mapOf("category" to category)))
                        }

                        post {
                            val id = UUID.fromString(call.parameters.getOrFail("id"))

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

                            call.respondRedirect("/config/category")
                        }
                    }
                }
            }
        }
    }
}
