package com.backcube.economyapp.data.remote.di

import com.backcube.economyapp.data.remote.repositories.impl.AccountRepositoryImpl
import com.backcube.economyapp.data.remote.repositories.impl.CategoryRepositoryImpl
import com.backcube.economyapp.data.remote.repositories.impl.TransactionRepositoryImpl
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.domain.repositories.CategoryRepository
import com.backcube.economyapp.domain.repositories.TransactionRepository
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