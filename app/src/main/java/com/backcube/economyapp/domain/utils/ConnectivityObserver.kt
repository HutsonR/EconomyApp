package com.backcube.economyapp.domain.utils

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Boolean>
}
