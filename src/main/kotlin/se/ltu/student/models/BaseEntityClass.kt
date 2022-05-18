package se.ltu.student.models

import org.jetbrains.exposed.dao.*

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