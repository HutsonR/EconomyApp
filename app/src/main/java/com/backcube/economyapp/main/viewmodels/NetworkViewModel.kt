package com.backcube.economyapp.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backcube.domain.repositories.SyncRepository
import com.backcube.domain.utils.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver,
    private val syncRepository: SyncRepository
) : ViewModel() {

    val isConnected = connectivityObserver.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)

    init {
        viewModelScope.launch {
            isConnected
                .filter { it }
                .collect {
                    syncRepository.syncData()
                }
        }
    }

}
