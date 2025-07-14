package com.backcube.economyapp.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backcube.domain.utils.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class NetworkViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
}
