package com.backcube.data.remote.impl.network

import com.backcube.local.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {

    private const val BASE_URL = "https://shmr-finance.ru/api/v1/"

    val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor(BuildConfig.MAIN_API_KEY))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
