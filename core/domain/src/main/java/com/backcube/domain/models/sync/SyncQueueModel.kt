package com.backcube.domain.models.sync

import kotlin.random.Random

/**
 * @param id ID синхронизации
 * @param operation Тип операции (создание, удаление)
 * @param entityType Тип сущности (категория, транзакция)
 * @param targetId ID сущности в базе данных сервера. Нужен только для DELETE. Для CREATE может быть null (сервер сам выдаст ID).
 * @param payload JSON тела запроса
 * @param createdAt Время создания записи в базе данных
 * */
data class SyncQueueModel (
    val id: Int = -Random.nextInt(100000),
    val operation: SyncOperationType,
    val entityType: SyncEntityType,
    val targetId: Int?,
    val payload: String,
    val createdAt: Long = System.currentTimeMillis()
)

enum class SyncOperationType {
    CREATE, DELETE
}

enum class SyncEntityType {
    TRANSACTION
}
