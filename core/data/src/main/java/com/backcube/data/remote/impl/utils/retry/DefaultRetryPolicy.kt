package com.backcube.data.remote.impl.utils.retry

internal data class DefaultRetryPolicy(
    override val maxRetries: Int = 3,
    override val retryInterval: Long = 2000L
) : RetryPolicy