package com.backcube.data.local.impl.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val operation: SyncOperationType,
    val entityType: SyncEntityType,
    val targetId: Int?, // server ID или локальный временный ID
    val payload: String, // JSON тела запроса
    val createdAt: Long = System.currentTimeMillis()
)

enum class SyncOperationType { CREATE, UPDATE, DELETE }
enum class SyncEntityType { TRANSACTION, ACCOUNT, CATEGORY }