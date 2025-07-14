package com.backcube.data.remote.di

import com.backcube.data.remote.api.AccountApi
import com.backcube.data.remote.api.CategoriesApi
import com.backcube.data.remote.api.TransactionsApi
import com.backcube.data.remote.network.NetworkProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideAccountApi(): AccountApi {
        return NetworkProvider.retrofit.create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriesApi(): CategoriesApi {
        return NetworkProvider.retrofit.create(CategoriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionsApi(): TransactionsApi {
        return NetworkProvider.retrofit.create(TransactionsApi::class.java)
    }
}