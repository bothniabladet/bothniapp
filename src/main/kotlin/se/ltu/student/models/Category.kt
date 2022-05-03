package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

class Category(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Category>(Categories)

    var name by Categories.name
    var description by Categories.description
}

object Categories : UUIDTable() {
    val parentID = reference("parent_id", id).nullable()
    val name = varchar("caption", 256)
    val description = text("description").nullable()
}