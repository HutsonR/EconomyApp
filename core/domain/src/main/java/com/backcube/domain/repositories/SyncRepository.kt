package com.backcube.domain.repositories

interface SyncRepository {
    suspend fun syncData(): Result<Unit>
}