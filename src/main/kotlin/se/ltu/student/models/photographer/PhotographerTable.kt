package se.ltu.student.models.photographer

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import se.ltu.student.models.imagesource.ImageSourceTable

object PhotographerTable : UUIDTable("photographers") {
    val givenName = varchar("given_name", 256)
    val familyName = varchar("family_name", 256)
    val email = varchar("email", 256).nullable()
    val phone = varchar("phone", 20).nullable()
    val imageSource = reference("source", ImageSourceTable, onDelete = ReferenceOption.SET_NULL).nullable()
}