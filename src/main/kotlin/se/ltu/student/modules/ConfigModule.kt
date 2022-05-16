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
import se.ltu.student.models.Category
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
                            Category.all().map(Category::toModel)
                        }
                        call.respondFMT(FreeMarkerContent("config/category/index.ftl", mapOf("categories" to categories)))
                    }

                    post {
                        val formParameters = call.receiveParameters()

                        val name = formParameters.getOrFail("name")

                        transaction {
                            Category.new {
                                this.name = name
                            }
                        }

                        call.respondRedirect("/config/category")
                    }

                    route("/{id}") {
                        get {
                            val id = UUID.fromString(call.parameters.getOrFail("id"))

                            val category = transaction {
                                Category.findById(id)?.toModel() ?: throw Error("No such category.")
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
                                val category = Category.findById(id) ?: throw Error("No such category.")
                                category.name = name
                                category.description = description
                                category.slug = slug
                            }

                            call.respondRedirect("/config/category")
                        }
                    }
                }
            }
        }
    }
}
