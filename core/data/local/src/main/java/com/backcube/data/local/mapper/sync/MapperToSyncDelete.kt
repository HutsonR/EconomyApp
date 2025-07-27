package com.backcube.data.local.mapper.sync

import com.backcube.domain.models.sync.SyncEntityType
import com.backcube.domain.models.sync.SyncOperationType
import com.backcube.domain.models.sync.SyncQueueModel

fun mapToSyncTransactionDelete(id: Int): SyncQueueModel = SyncQueueModel(
    operation = SyncOperationType.DELETE,
    entityType = SyncEntityType.TRANSACTION,
    targetId = id,
    payload = ""
)