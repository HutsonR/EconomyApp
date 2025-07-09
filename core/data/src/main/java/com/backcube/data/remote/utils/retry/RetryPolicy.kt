package com.backcube.data.remote.utils.retry

interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}