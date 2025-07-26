package com.backcube.data.local.impl.datasources

import com.backcube.data.local.api.SyncLocalDataSource
import com.backcube.data.local.impl.dao.SyncQueueDao
import com.backcube.data.local.impl.entities.SyncQueueEntity
import com.backcube.domain.models.sync.SyncQueueModel
import javax.inject.Inject

internal class SyncLocalDataSourceImpl @Inject constructor(
    private val syncQueueDao: SyncQueueDao
): SyncLocalDataSource {

    override suspend fun getAllPendingOperations(): List<SyncQueueModel> {
        return syncQueueDao.getAllPendingOperations().map { it.toDomain() }
    }

    override suspend fun enqueueOperation(operation: SyncQueueModel) {
        syncQueueDao.enqueue(SyncQueueEntity.toEntity(operation))
    }

    override suspend fun removeOperation(operation: SyncQueueModel) {
        syncQueueDao.remove(SyncQueueEntity.toEntity(operation))
    }

    override suspend fun removeOperationById(id: Int) {
        syncQueueDao.removeById(id)
    }

    override suspend fun removeOperationByTargetId(id: Int) {
        syncQueueDao.removeByTargetId(id)
    }

}