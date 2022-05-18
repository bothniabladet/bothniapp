package se.ltu.student.models.category

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import java.util.*

class CategoryEntity(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CategoryEntity>(CategoryTable)

    var parent by CategoryEntity optionalReferencedOn CategoryTable.parent
    var name by CategoryTable.name
    var slug by CategoryTable.slug
    var description by CategoryTable.description
}