package com.backcube.economyapp.domain.usecases.impl.common

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountNotifierUseCase @Inject constructor() {
    private val _refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1, replay = 1)
    val refreshTrigger: SharedFlow<Unit> = _refreshTrigger

    fun notifyAccountChanged() {
        _refreshTrigger.tryEmit(Unit)
    }
}
