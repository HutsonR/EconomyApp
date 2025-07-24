package com.backcube.data.local.di

import android.content.Context
import com.backcube.data.local.impl.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): AppDatabase =
        AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideArticlesDao(appDatabase: AppDatabase) =
        appDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideAccountsDao(appDatabase: AppDatabase) =
        appDatabase.accountDao()

    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: AppDatabase) =
        appDatabase.transactionDao()

    @Provides
    @Singleton
    fun provideSyncQueueDao(appDatabase: AppDatabase) =
        appDatabase.syncQueueDao()

}