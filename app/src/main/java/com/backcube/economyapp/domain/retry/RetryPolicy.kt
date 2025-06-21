package com.backcube.economyapp.domain.retry

interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}