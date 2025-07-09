package com.backcube.economyapp.data.remote.utils.retry

interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}