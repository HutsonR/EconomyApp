package com.backcube.economyapp.domain.di

import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.CategoryUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.AccountUseCaseImpl
import com.backcube.economyapp.domain.usecases.impl.CategoryUseCaseImpl
import com.backcube.economyapp.domain.usecases.impl.TransactionUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindAccountUseCase(accountUseCaseImpl: AccountUseCaseImpl): AccountUseCase

    @Binds
    fun bindCategoryUseCase(categoryUseCaseImpl: CategoryUseCaseImpl): CategoryUseCase

    @Binds
    fun bindTransactionUseCase(transactionUseCaseImpl: TransactionUseCaseImpl): TransactionUseCase

}