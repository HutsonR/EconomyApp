package com.backcube.domain.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Boolean>
    fun isInternetAvailable(): Boolean
}
