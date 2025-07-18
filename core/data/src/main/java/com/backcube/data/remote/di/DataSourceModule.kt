package com.backcube.data.remote.di

import com.backcube.data.remote.api.AccountRemoteDataSource
import com.backcube.data.remote.api.CategoriesRemoteDataSource
import com.backcube.data.remote.api.TransactionsRemoteDataSource
import com.backcube.data.remote.impl.datasources.AccountRemoteDataSourceImpl
import com.backcube.data.remote.impl.datasources.CategoriesRemoteDataSourceImpl
import com.backcube.data.remote.impl.datasources.TransactionsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindAccountRemoteDataSource(
        accountRemoteDataSourceImpl: AccountRemoteDataSourceImpl
    ): AccountRemoteDataSource

    @Binds
    fun bindCategoriesRemoteDataSource(
        categoriesRemoteDataSourceImpl: CategoriesRemoteDataSourceImpl
    ): CategoriesRemoteDataSource

    @Binds
    fun bindTransactionsRemoteDataSource(
        transactionsRemoteDataSourceImpl: TransactionsRemoteDataSourceImpl
    ): TransactionsRemoteDataSource

}