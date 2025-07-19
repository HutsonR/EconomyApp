package com.backcube.data.remote.impl.utils.retry

interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}