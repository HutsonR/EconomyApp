package com.backcube.economyapp.di.domain

import com.backcube.economyapp.domain.utils.qualifiers.DefaultDispatchers
import com.backcube.economyapp.domain.utils.qualifiers.IoDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class QualifiersModule {
    @Provides
    @IoDispatchers
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @DefaultDispatchers
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}