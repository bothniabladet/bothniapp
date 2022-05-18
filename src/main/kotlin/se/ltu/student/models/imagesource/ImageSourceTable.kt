package se.ltu.student.models.imagesource

import org.jetbrains.exposed.dao.id.UUIDTable

object ImageSourceTable : UUIDTable("imagesources") {
    val name = varchar("name", 256)
    val website = varchar("website", 256).nullable()
}