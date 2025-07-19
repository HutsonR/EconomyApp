package com.backcube.data.local.impl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.backcube.data.local.impl.entities.accounts.AccountEntity
import com.backcube.data.local.impl.entities.accounts.AccountResponseEntity

@Dao
interface AccountDao {
    // AccountEntity (список счетов)
    @Query("SELECT * FROM accounts_api")
    suspend fun getAllAccounts(): List<AccountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccounts(accounts: List<AccountEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(entity: AccountEntity)

    @Query("DELETE FROM accounts_api")
    suspend fun clearAccounts()

    // AccountResponseEntity (подробный счет)
    @Query("SELECT * FROM accounts_response WHERE id = :id")
    suspend fun getAccountDetails(id: Int): AccountResponseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccountDetails(account: AccountResponseEntity)

    @Query("DELETE FROM accounts_response")
    suspend fun clearAccountDetails()
}

