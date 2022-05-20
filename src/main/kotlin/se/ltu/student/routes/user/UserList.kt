package se.ltu.student.routes.user

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import se.ltu.student.extensions.respondFMT
import se.ltu.student.models.user.UserEntity
import se.ltu.student.models.user.UserModel
import se.ltu.student.models.user.toModel

fun Route.listUsersRoute() {
    get {
        val users = transaction {
            UserEntity.all().map(UserEntity::toModel).sortedBy(UserModel::familyName)
        }

        call.respondFMT(FreeMarkerContent("user/list.ftl", mapOf("users" to users)))
    }
}