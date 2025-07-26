package com.backcube.data.local.impl.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.backcube.domain.models.sync.SyncEntityType
import com.backcube.domain.models.sync.SyncOperationType
import com.backcube.domain.models.sync.SyncQueueModel

@Entity(tableName = "sync_queue")
internal data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val operation: SyncOperationType,
    val entityType: SyncEntityType,
    val targetId: Int?,
    val payload: String,
    val createdAt: Long = System.currentTimeMillis()
) {
    internal fun toDomain() = SyncQueueModel(
        id = id,
        operation = operation,
        entityType = entityType,
        targetId = targetId,
        payload = payload,
        createdAt = createdAt
    )
    companion object {
        internal fun toEntity(syncQueueModel: SyncQueueModel) = SyncQueueEntity(
            id = syncQueueModel.id,
            operation = syncQueueModel.operation,
            entityType = syncQueueModel.entityType,
            targetId = syncQueueModel.targetId,
            payload = syncQueueModel.payload,
            createdAt = syncQueueModel.createdAt
        )
    }
}
