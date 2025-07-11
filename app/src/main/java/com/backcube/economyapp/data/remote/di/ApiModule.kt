package com.backcube.economyapp.data.remote.di

import com.backcube.economyapp.data.remote.api.AccountApi
import com.backcube.economyapp.data.remote.api.CategoriesApi
import com.backcube.economyapp.data.remote.api.TransactionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideAccountApi(retrofit: Retrofit): AccountApi {
        return retrofit.create(AccountApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi {
        return retrofit.create(CategoriesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi {
        return retrofit.create(TransactionsApi::class.java)
    }
}