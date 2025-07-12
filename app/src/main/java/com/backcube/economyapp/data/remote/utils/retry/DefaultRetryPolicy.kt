package com.backcube.economyapp.data.remote.utils.retry

data class DefaultRetryPolicy(
    override val maxRetries: Int = 3,
    override val retryInterval: Long = 2000L
) : RetryPolicy