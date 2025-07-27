package com.backcube.data.local.di

import com.backcube.data.local.api.datastore.PreferencesDataSource
import com.backcube.data.local.api.db.AccountLocalDataSource
import com.backcube.data.local.api.db.CategoriesLocalDataSource
import com.backcube.data.local.api.db.TransactionsLocalDataSource
import com.backcube.data.local.api.sync.SyncLocalDataSource
import com.backcube.data.local.impl.datasources.datastore.PreferencesDataSourceImpl
import com.backcube.data.local.impl.datasources.db.AccountLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.db.CategoriesLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.db.TransactionsLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.sync.SyncLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface LocalDataSourceModule {

    @Binds
    @Singleton
    fun bindAccountLocalDataSource(
        accountLocalDataSourceImpl: AccountLocalDataSourceImpl
    ): AccountLocalDataSource

    @Binds
    @Singleton
    fun bindCategoriesLocalDataSource(
        categoriesLocalDataSourceImpl: CategoriesLocalDataSourceImpl
    ): CategoriesLocalDataSource

    @Binds
    @Singleton
    fun bindTransactionsLocalDataSource(
        transactionsLocalDataSourceImpl: TransactionsLocalDataSourceImpl
    ): TransactionsLocalDataSource

    @Binds
    @Singleton
    fun bindSyncLocalDataSource(
        syncLocalDataSourceImpl: SyncLocalDataSourceImpl
    ): SyncLocalDataSource

    @Binds
    @Singleton
    fun bindPreferencesDataSource(
        preferencesDataSourceImpl: PreferencesDataSourceImpl
    ): PreferencesDataSource

}