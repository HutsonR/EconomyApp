package com.backcube.economyapp.features.expenses

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.features.expenses.store.models.ExpenseEffect
import com.backcube.economyapp.features.expenses.store.models.ExpenseIntent
import com.backcube.economyapp.features.expenses.store.models.ExpenseState
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        modifyState { copy(isLoading = true) }
        val accountsResult = accountUseCase.getAccounts()

        accountsResult.fold(
            onSuccess = { accounts ->
                val accountId = accounts.firstOrNull()?.id ?: 1
                val transactionResult = transactionUseCase.getAccountTransactions(
                    accountId = accountId,
                    startDate = getState().expenseDate,
                    endDate = getState().expenseDate
                )

                transactionResult.fold(
                    onSuccess = { transactions ->
                        val filteredTransactions = transactions.filter { !it.category.isIncome }
                        val totalTransactionsSum = filteredTransactions.sumOf { it.amount }

                        modifyState {
                            copy(
                                items = filteredTransactions,
                                totalSum = totalTransactionsSum
                            )
                        }
                    },
                    onFailure = {
                        it.printStackTrace()
                        effect(ExpenseEffect.ShowClientError)
                    }
                )
            },
            onFailure = {
                it.printStackTrace()
                effect(ExpenseEffect.ShowClientError)
            }
        )

        modifyState { copy(isLoading = false) }
    }

    fun handleIntent(intent: ExpenseIntent) {
        when(intent) {
            ExpenseIntent.GoToHistory -> effect(ExpenseEffect.NavigateToHistory)
        }
    }
}