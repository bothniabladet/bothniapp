package se.ltu.student.models.category

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption

object CategoryTable : UUIDTable("categories") {
    val parent = reference("parent", id, onDelete = ReferenceOption.CASCADE).nullable()
    val name = varchar("name", 256)
    val slug = varchar("slug", 64).uniqueIndex().nullable()
    val description = text("description", eagerLoading = true).nullable()
}