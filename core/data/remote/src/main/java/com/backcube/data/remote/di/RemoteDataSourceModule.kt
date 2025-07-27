package com.backcube.data.remote.di

import com.backcube.data.remote.api.AccountRemoteDataSource
import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.datasources.AccountRemoteDataSourceImpl
import com.backcube.data.remote.impl.datasources.CategoriesRemoteDataSourceImpl
import com.backcube.data.remote.impl.datasources.TransactionsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface RemoteDataSourceModule {

    @Binds
    @Singleton
    fun bindAccountRemoteDataSource(
        accountRemoteDataSourceImpl: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource

    @Binds
    @Singleton
    fun bindCategoriesRemoteDataSource(
        categoriesRemoteDataSourceImpl: CategoriesRemoteDataSourceImpl
    ): CategoriesRemoteDataSource

    @Binds
    @Singleton
    fun bindTransactionsRemoteDataSource(
        transactionsRemoteDataSourceImpl: TransactionsRemoteDataSourceImpl
    ): TransactionsRemoteDataSource

}