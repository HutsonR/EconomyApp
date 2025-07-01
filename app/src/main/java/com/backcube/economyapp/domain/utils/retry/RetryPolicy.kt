package com.backcube.economyapp.domain.utils.retry

interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}