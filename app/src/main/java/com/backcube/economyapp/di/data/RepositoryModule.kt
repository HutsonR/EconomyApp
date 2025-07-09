package com.backcube.economyapp.di.data

import com.backcube.data.remote.repositories.impl.AccountRepositoryImpl
import com.backcube.data.remote.repositories.impl.CategoryRepositoryImpl
import com.backcube.data.remote.repositories.impl.TransactionRepositoryImpl
import com.backcube.domain.repositories.AccountRepository
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindTransactionRepository(transactionRepositoryImpl: TransactionRepositoryImpl): TransactionRepository

}