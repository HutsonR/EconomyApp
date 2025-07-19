package com.backcube.domain.utils

import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<Result<T>>.collectResult(
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (Throwable) -> Unit
) {
    collect { result ->
        result
            .onSuccess { onSuccess(it) }
            .onFailure { onFailure(it) }
    }
}

