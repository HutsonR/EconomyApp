package com.backcube.data.local.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.backcube.data.local.impl.entities.transactions.TransactionEntity
import com.backcube.data.local.impl.entities.transactions.TransactionResponseEntity

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(entity: TransactionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntities(entities: List<TransactionEntity>)

    @Query("DELETE FROM transactions_api WHERE id = :id")
    suspend fun deleteEntityById(id: Int)

    @Query("SELECT * FROM transactions_api WHERE id = :id")
    suspend fun getEntityById(id: Int): TransactionEntity?

    @Query("SELECT * FROM transactions_api")
    suspend fun getAllEntities(): List<TransactionEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponse(response: TransactionResponseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponses(responses: List<TransactionResponseEntity>)

    @Query("DELETE FROM transactions_response WHERE id = :id")
    suspend fun deleteResponseById(id: Int): Int

    @Query("SELECT * FROM transactions_response WHERE id = :id")
    suspend fun getResponseById(id: Int): TransactionResponseEntity

    @Query("SELECT * FROM transactions_response")
    suspend fun getAllResponses(): List<TransactionResponseEntity>

    @Query("""
        SELECT * FROM transactions_response
        WHERE account_id = :accountId
        AND transaction_date BETWEEN :startDate AND :endDate
    """)
    suspend fun getResponsesByAccountAndPeriod(
        accountId: Int,
        startDate: Long,
        endDate: Long
    ): List<TransactionResponseEntity>
}


