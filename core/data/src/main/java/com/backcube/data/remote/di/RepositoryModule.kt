package com.backcube.data.remote.di

import com.backcube.data.remote.impl.repositories.impl.AccountRepositoryImpl
import com.backcube.data.remote.impl.repositories.impl.CategoryRepositoryImpl
import com.backcube.data.remote.impl.repositories.impl.TransactionRepositoryImpl
import com.backcube.domain.repositories.AccountRepository
import com.backcube.domain.repositories.CategoryRepository
import com.backcube.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindTransactionRepository(transactionRepositoryImpl: TransactionRepositoryImpl): TransactionRepository

}