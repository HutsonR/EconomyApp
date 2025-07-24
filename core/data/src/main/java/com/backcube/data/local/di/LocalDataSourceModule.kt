package com.backcube.data.local.di

import com.backcube.data.local.api.AccountLocalDataSource
import com.backcube.data.local.api.CategoriesLocalDataSource
import com.backcube.data.local.api.SyncLocalDataSource
import com.backcube.data.local.api.TransactionsLocalDataSource
import com.backcube.data.local.impl.datasources.AccountLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.CategoriesLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.SyncLocalDataSourceImpl
import com.backcube.data.local.impl.datasources.TransactionsLocalDataSourceImpl
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

}