package com.backcube.domain.di

import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.CategoryUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.domain.usecases.impl.AccountUseCaseImpl
import com.backcube.domain.usecases.impl.CategoryUseCaseImpl
import com.backcube.domain.usecases.impl.TransactionUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {
    @Binds
    fun bindAccountUseCase(accountUseCaseImpl: AccountUseCaseImpl): AccountUseCase

    @Binds
    fun bindCategoryUseCase(categoryUseCaseImpl: CategoryUseCaseImpl): CategoryUseCase

    @Binds
    fun bindTransactionUseCase(transactionUseCaseImpl: TransactionUseCaseImpl): TransactionUseCase
}