package se.ltu.student.models

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.*

fun currentDateTime(): LocalDateTime = LocalDateTime.now()

abstract class BaseUUIDTable(name: String) : UUIDTable(name) {
    val createdAt = datetime("createdAt").clientDefault { currentDateTime() }
    val updatedAt = datetime("updatedAt").nullable()
}

abstract class BaseUUIDEntity(id: EntityID<UUID>, table: BaseUUIDTable) : UUIDEntity(id) {
    val createdAt by table.createdAt
    var updatedAt by table.updatedAt
}

abstract class BaseUUIDEntityClass<E : BaseUUIDEntity>(table: BaseUUIDTable) : UUIDEntityClass<E>(table) {

    init {
        EntityHook.subscribe { action ->
            if (action.changeType == EntityChangeType.Updated) {
                try {
                    action.toEntity(this)?.updatedAt = currentDateTime()
                } catch (e: Exception) {
                    //nothing much to do here
                }
            }
        }
    }
}