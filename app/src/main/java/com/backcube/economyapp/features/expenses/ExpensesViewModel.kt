package com.backcube.economyapp.features.expenses

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.features.expenses.store.models.ExpenseEffect
import com.backcube.economyapp.features.expenses.store.models.ExpenseIntent
import com.backcube.economyapp.features.expenses.store.models.ExpenseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<ExpenseState, ExpenseEffect>(ExpenseState()) {

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
            val result = transactionUseCase.getAccountTransactions(
                accountId = accountUseCase.getAccounts().firstOrNull()?.id ?: 1,
                startDate = null,
                endDate = null
            ).filter { !it.category.isIncome }
            val totalSum = result.sumOf { it.amount }

            modifyState {
                copy(
                    items = result,
                    totalSum = totalSum
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            effect(ExpenseEffect.ShowClientError)
        } finally {
            modifyState { copy(isLoading = false) }
        }
    }

    fun handleIntent(intent: ExpenseIntent) {
        when(intent) {
            ExpenseIntent.GoToHistory -> effect(ExpenseEffect.NavigateToHistory)
        }
    }
}