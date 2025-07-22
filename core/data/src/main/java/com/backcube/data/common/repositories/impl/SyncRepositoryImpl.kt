package com.backcube.data.common.repositories.impl

import com.backcube.data.local.api.SyncLocalDataSource
import com.backcube.data.local.impl.datasources.TransactionsLocalDataSourceImpl
import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.models.request.transactions.TransactionRequestApiModel
import com.backcube.domain.models.sync.SyncEntityType
import com.backcube.domain.models.sync.SyncOperationType
import com.backcube.domain.models.sync.SyncQueueModel
import com.backcube.domain.repositories.SyncRepository
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
    private val syncLocalDataSource: SyncLocalDataSource,
    private val transactionsLocalDataSourceImpl: TransactionsLocalDataSourceImpl,
    private val transactionsRemoteDataSource: TransactionsRemoteDataSource,
) : SyncRepository {

    override suspend fun syncData(): Result<Unit> = runCatching {
        removeConflictingOperations()
        val queue = syncLocalDataSource.getAllPendingOperations()

        for (entry in queue) {
            try {
                when (entry.entityType) {
                    SyncEntityType.TRANSACTION -> handleTransaction(entry)
                }
                syncLocalDataSource.removeOperationById(entry.id)
            } catch (e: Exception) {
                e.printStackTrace()
                break
            }
        }
    }

    private suspend fun removeConflictingOperations() {
        val operations = syncLocalDataSource.getAllPendingOperations()
        val deletes = operations.filter { it.operation == SyncOperationType.DELETE }
            .mapNotNull { it.targetId }
            .toSet()

        val conflictingCreates = operations.filter { op ->
            op.operation == SyncOperationType.CREATE && op.id in deletes
        }
        conflictingCreates.forEach {
            syncLocalDataSource.removeOperationById(it.id)
        }
    }

    private suspend fun handleTransaction(entry: SyncQueueModel) {
        when (entry.operation) {
            SyncOperationType.CREATE -> {
                val model = Json.decodeFromString<TransactionRequestApiModel>(entry.payload)
                transactionsRemoteDataSource.createTransaction(model)
            }

            SyncOperationType.DELETE -> {
                val id = entry.targetId ?: throw IllegalArgumentException("Missing targetId for DELETE")
                if (id >= 0) {
                    transactionsRemoteDataSource.deleteTransaction(id)
                } else {
                    transactionsLocalDataSourceImpl.deleteTransaction(id)
                }
            }
        }
    }
}