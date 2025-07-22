package com.backcube.data.local.impl.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.backcube.data.local.impl.dao.AccountDao
import com.backcube.data.local.impl.dao.CategoryDao
import com.backcube.data.local.impl.dao.SyncQueueDao
import com.backcube.data.local.impl.dao.TransactionDao
import com.backcube.data.local.impl.entities.SyncQueueEntity
import com.backcube.data.local.impl.entities.accounts.AccountEntity
import com.backcube.data.local.impl.entities.accounts.AccountResponseEntity
import com.backcube.data.local.impl.entities.categories.CategoryEntity
import com.backcube.data.local.impl.entities.converters.AccountBriefEntityConverter
import com.backcube.data.local.impl.entities.converters.CategorySerialEntityConverter
import com.backcube.data.local.impl.entities.converters.StatListConverter
import com.backcube.data.local.impl.entities.transactions.TransactionEntity
import com.backcube.data.local.impl.entities.transactions.TransactionResponseEntity

@Database(
    entities = [
        AccountEntity::class,
        AccountResponseEntity::class,
        CategoryEntity::class,
        TransactionEntity::class,
        TransactionResponseEntity::class,
        SyncQueueEntity::class
    ],
    version = 7,
    exportSchema = false
)
@TypeConverters(
    StatListConverter::class,
    AccountBriefEntityConverter::class,
    CategorySerialEntityConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun syncQueueDao(): SyncQueueDao

    companion object {
        private const val DB_NAME = "FinanceAppDatabase"
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(
            context: Context
        ): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
            }
            val database = Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = DB_NAME
            ).fallbackToDestructiveMigration(false).build()
            INSTANCE = database
            return database
        }
    }
}