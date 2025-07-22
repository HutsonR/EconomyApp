package com.backcube.data.local.api

import com.backcube.domain.models.sync.SyncQueueModel

interface SyncLocalDataSource {
    suspend fun getAllPendingOperations(): List<SyncQueueModel>
    suspend fun enqueueOperation(operation: SyncQueueModel)
    suspend fun removeOperation(operation: SyncQueueModel)
    suspend fun removeOperationById(id: Int)
    suspend fun removeOperationByTargetId(id: Int)
}