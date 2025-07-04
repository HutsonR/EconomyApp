package com.backcube.economyapp.features.income

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.features.income.store.models.IncomeEffect
import com.backcube.economyapp.features.income.store.models.IncomeIntent
import com.backcube.economyapp.features.income.store.models.IncomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<IncomeState, IncomeEffect>(IncomeState()) {

    init {
        viewModelScope.launch {
            fetchData()
            accountNotifierUseCase.refreshTrigger.collect {
                fetchData()
            }
        }
    }

    private suspend fun fetchData() {
        try {
            modifyState { copy(isLoading = true) }
            val accountId = accountUseCase.getAccounts().firstOrNull()?.id ?: 1
            val result = transactionUseCase.getAccountTransactions(
                accountId = accountId,
                startDate = null,
                endDate = null
            ).filter { it.category.isIncome }
            val totalSum = result.sumOf { it.amount }

            modifyState {
                copy(
                    items = result,
                    totalSum = totalSum
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            effect(IncomeEffect.ShowClientError)
        } finally {
            modifyState { copy(isLoading = false) }
        }
    }

    fun handleIntent(intent: IncomeIntent) {
        when(intent) {
            IncomeIntent.GoToHistory -> effect(IncomeEffect.NavigateToHistory)
        }
    }
}