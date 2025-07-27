package com.backcube.data.common.repositories.di

import android.content.Context
import com.backcube.data.common.utils.NetworkConnectivityObserver
import com.backcube.domain.utils.ConnectivityObserver
import dagger.Module
import dagger.Provides

@Module
internal class NetworkConnectivityModule {

    @Provides
    fun provideConnectivityObserver(context: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

}