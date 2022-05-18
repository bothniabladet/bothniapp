package se.ltu.student.models

import org.jetbrains.exposed.dao.EntityChangeType
import org.jetbrains.exposed.dao.EntityHook
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.toEntity

abstract class BaseEntityClass<E : BaseEntity>(table: BaseTable) : UUIDEntityClass<E>(table) {
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