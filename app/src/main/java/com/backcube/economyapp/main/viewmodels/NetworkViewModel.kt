package com.backcube.economyapp.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.domain.utils.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val isConnected = connectivityObserver.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)
}
