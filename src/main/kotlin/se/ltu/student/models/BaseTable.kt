package se.ltu.student.models

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

fun currentDateTime(): LocalDateTime = LocalDateTime.now()

abstract class BaseTable(name: String) : UUIDTable(name) {
    val createdAt = datetime("createdAt").clientDefault { currentDateTime() }
    val updatedAt = datetime("updatedAt").nullable()
}