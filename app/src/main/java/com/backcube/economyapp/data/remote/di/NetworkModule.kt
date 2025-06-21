package com.backcube.economyapp.data.remote.di

import android.content.Context
import com.backcube.economyapp.BuildConfig
import com.backcube.economyapp.data.remote.utils.NetworkConnectivityObserver
import com.backcube.economyapp.domain.utils.ConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val original: Request = chain.request()
            val token: String = BuildConfig.MAIN_API_KEY

            val requestWithToken = if (token.isNotBlank()) {
                original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else original

            chain.proceed(requestWithToken)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: Interceptor
    ): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logger)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMainRetrofit(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideConnectivityObserver(@ApplicationContext context: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }


}