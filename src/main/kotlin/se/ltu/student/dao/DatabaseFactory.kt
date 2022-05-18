package se.ltu.student.dao

import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.models.category.CategoryTable
import se.ltu.student.models.image.ImageTable
import se.ltu.student.models.imagesource.ImageSourceTable
import se.ltu.student.models.photographer.PhotographerTable
import se.ltu.student.models.upload.ImageUploadTable
import se.ltu.student.models.upload.UploadTable
import se.ltu.student.models.user.UserTable

object DatabaseFactory {
    fun init(environment: ApplicationEnvironment) {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = environment.config.propertyOrNull("ktor.database.jdbcURL")?.getString()
            ?: "jdbc:postgresql://localhost:5432/bothniabladet"
        val user = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: "postgres"
        val password = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: "postgres"
        val database = Database.connect(url = jdbcURL, driver = driverClassName, user = user, password = password)
        transaction(database) {

            //SchemaUtils.drop(Photographers, ImageSources, ImageUploads, Uploads, Images, Categories, Users, inBatch = true)

            SchemaUtils.create(
                UserTable,
                ImageSourceTable,
                PhotographerTable,
                CategoryTable,
                ImageTable,
                UploadTable,
                ImageUploadTable
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}