package com.backcube.economyapp.data.remote.di

import com.backcube.economyapp.data.remote.repositories.fake.FakeAccountRepositoryImpl
import com.backcube.economyapp.data.remote.repositories.fake.FakeCategoryRepositoryImpl
import com.backcube.economyapp.data.remote.repositories.fake.FakeTransactionRepositoryImpl
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.domain.repositories.CategoryRepository
import com.backcube.economyapp.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAccountRepository(fakeAccountRepositoryImpl: FakeAccountRepositoryImpl): AccountRepository

    @Binds
    fun bindCategoryRepository(fakeCategoryRepositoryImpl: FakeCategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindTransactionRepository(fakeTransactionRepositoryImpl: FakeTransactionRepositoryImpl): TransactionRepository

}