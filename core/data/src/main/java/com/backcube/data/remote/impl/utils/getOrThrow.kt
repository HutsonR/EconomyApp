package com.backcube.data.remote.impl.utils

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.getOrThrow(): T {
    if (isSuccessful) {
        return body() ?: throw IllegalStateException("Response body is null")
    } else {
        throw HttpException(this)
    }
}