package se.ltu.student.dao

import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*
import se.ltu.student.models.Users
import io.ktor.server.application.*
import se.ltu.student.models.Image
import se.ltu.student.models.Images

object DatabaseFactory {
    fun init(environment: ApplicationEnvironment) {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = environment.config.propertyOrNull("ktor.database.jdbcURL")?.getString() ?: "jdbc:postgresql://localhost:5432/bothniabladet"
        val user = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: "postgres"
        val password = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: "postgres"
        val database = Database.connect(url = jdbcURL, driver = driverClassName, user = user, password = password)
        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(Images)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}