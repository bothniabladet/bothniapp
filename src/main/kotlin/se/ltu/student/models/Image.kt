package se.ltu.student.models

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.*

class Image(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Image>(Images)

    var caption by Images.caption
    var description by Images.description
    var path by Images.path
    var size by Images.size
    var width by Images.width
    var height by Images.height
}

object Images : UUIDTable() {
    val parentID = reference("parent_id", id).nullable()
    val caption = varchar("caption", 256)
    val description = text("description").nullable()
    val path = varchar("path", 256).uniqueIndex().nullable()
    val size = integer("size")
    val width = integer("width").default(0)
    val height = integer("height").default(0)
}