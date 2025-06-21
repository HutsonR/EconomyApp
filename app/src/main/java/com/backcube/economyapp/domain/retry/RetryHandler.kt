package com.backcube.economyapp.domain.retry

import kotlinx.coroutines.delay
import retrofit2.HttpException
import javax.inject.Inject

class RetryHandler @Inject constructor(
    private val retryPolicy: RetryPolicy
) {
    suspend fun <T> executeWithRetry(
        block: suspend () -> T
    ): T {
        var retryCount = 0
        var lastException: Exception? = null

        while (retryCount < retryPolicy.maxRetries) {
            try {
                return block()
            } catch (e: Exception) {
                lastException = e
                val isServerError = (e as? HttpException)?.code() in 500..599
                if (!isServerError) {
                    throw e
                }
                retryCount++
                delay(retryPolicy.retryInterval)
            }
        }
        throw lastException ?: IllegalStateException("Retries exhausted")
    }
}