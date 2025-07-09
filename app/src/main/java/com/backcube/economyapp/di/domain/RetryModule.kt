package com.backcube.economyapp.di.domain

import com.backcube.economyapp.data.remote.utils.retry.DefaultRetryPolicy
import com.backcube.economyapp.data.remote.utils.retry.RetryHandler
import com.backcube.economyapp.data.remote.utils.retry.RetryPolicy
import com.backcube.economyapp.domain.utils.qualifiers.IoDispatchers
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