package com.backcube.economyapp.features.account.store.models

sealed interface AccountEffect {
    data object ShowClientError : AccountEffect
}