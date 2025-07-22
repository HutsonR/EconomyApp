package com.backcube.data.local.impl.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.backcube.data.local.impl.entities.SyncQueueEntity

@Dao
interface SyncQueueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enqueue(entry: SyncQueueEntity)

    @Query("SELECT * FROM sync_queue ORDER BY createdAt ASC")
    suspend fun getAllPendingOperations(): List<SyncQueueEntity>

    @Delete
    suspend fun remove(entry: SyncQueueEntity)

    @Query("DELETE FROM sync_queue WHERE id = :id")
    suspend fun removeById(id: Int)

    @Query("DELETE FROM sync_queue WHERE targetId = :id")
    suspend fun removeByTargetId(id: Int)
}
