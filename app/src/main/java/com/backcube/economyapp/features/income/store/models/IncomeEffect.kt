package com.backcube.economyapp.features.income.store.models

sealed interface IncomeEffect {
    data object NavigateToHistory : IncomeEffect
    data object ShowClientError : IncomeEffect
}