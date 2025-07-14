package com.backcube.data.remote.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val token: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestWithToken = if (token.isNotBlank()) {
            original.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            original
        }

        return chain.proceed(requestWithToken)
    }
}
