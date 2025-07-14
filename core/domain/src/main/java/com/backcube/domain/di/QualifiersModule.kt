package com.backcube.domain.di

import com.backcube.domain.utils.qualifiers.DefaultDispatchers
import com.backcube.domain.utils.qualifiers.IoDispatchers
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
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