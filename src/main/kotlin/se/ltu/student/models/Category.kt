package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

@kotlinx.serialization.Serializable
data class CategoryModel constructor(
    val parent: CategoryModel?,
    val name: String,
    val description: String?
)

// Workaround for recursive initializer violation
fun resolve(category: Category?): CategoryModel? {
    return category?.toModel()
}

class Category(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Category>(Categories)

    var parent by Category optionalReferencedOn Categories.parent
    var name by Categories.name
    var description by Categories.description

    fun toModel() = CategoryModel(resolve(parent), name, description)
}

object Categories : UUIDTable() {
    val parent = reference("parent", id).nullable()
    val name = varchar("name", 256)
    val description = text("description", eagerLoading = true).nullable()
}