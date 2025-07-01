package com.backcube.economyapp.domain.utils.retry

import com.backcube.economyapp.domain.utils.qualifiers.IoDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class RetryHandler @Inject constructor(
    private val retryPolicy: RetryPolicy,
    @IoDispatchers private val dispatcher: CoroutineDispatcher,
) {
    suspend fun <T> executeWithRetry(
        block: suspend () -> T
    ): T = withContext(dispatcher) {
        var retryCount = 0
        var lastException: Exception? = null

        while (retryCount < retryPolicy.maxRetries) {
            try {
                return@withContext block()
            } catch (e: Exception) {
                if (e is CancellationException) throw e

                lastException = e
                val isServerError = (e as? HttpException)?.code() in 500..599
                if (!isServerError) throw e

                retryCount++
                delay(retryPolicy.retryInterval)
            }
        }
        throw lastException ?: IllegalStateException("Retries exhausted")
    }
}