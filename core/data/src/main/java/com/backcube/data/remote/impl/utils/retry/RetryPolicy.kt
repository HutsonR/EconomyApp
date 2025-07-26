package com.backcube.data.remote.impl.utils.retry

internal interface RetryPolicy {
    val maxRetries: Int
    val retryInterval: Long
}