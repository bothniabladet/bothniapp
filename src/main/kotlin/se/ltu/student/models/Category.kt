package se.ltu.student.models

import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import java.util.*

@kotlinx.serialization.Serializable
data class CategoryModel constructor(
    val id: String,
    val parent: CategoryModel?,
    val name: String,
    val slug: String,
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
    var slug by Categories.slug
    var description by Categories.description

    fun toModel() = CategoryModel(id.toString(), resolve(parent), name, slug ?: id.toString(), description)
}

object Categories : UUIDTable() {
    val parent = reference("parent", id, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 256)
    val slug = varchar("slug", 64).uniqueIndex().nullable()
    val description = text("description", eagerLoading = true).nullable()
}