package com.backcube.economyapp.domain.di

import com.backcube.economyapp.domain.qualifiers.IoDispatchers
import com.backcube.economyapp.domain.retry.DefaultRetryPolicy
import com.backcube.economyapp.domain.retry.RetryHandler
import com.backcube.economyapp.domain.retry.RetryPolicy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RetryModule {
    @Provides
    fun provideRetryPolicy(): RetryPolicy = DefaultRetryPolicy(
        maxRetries = 3,
        retryInterval = 2000L
    )

    @Provides
    fun provideRetryHandler(
        policy: RetryPolicy,
        @IoDispatchers dispatcher: CoroutineDispatcher
    ): RetryHandler = RetryHandler(
        policy,
        dispatcher
    )
}