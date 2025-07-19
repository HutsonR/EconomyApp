package com.backcube.data.remote.di

import android.content.Context
import com.backcube.data.common.utils.NetworkConnectivityObserver
import com.backcube.domain.utils.ConnectivityObserver
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun provideConnectivityObserver(context: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}