package com.backcube.transactions.presentation.list.viewmodels

import androidx.lifecycle.viewModelScope
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.domain.usecases.impl.common.UpdateNotifierUseCase
import com.backcube.transactions.presentation.list.models.TransactionEffect
import com.backcube.transactions.presentation.list.models.TransactionIntent
import com.backcube.transactions.presentation.list.models.TransactionState
import com.backcube.ui.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TransactionsViewModel @AssistedInject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase,
    private val updateNotifierUseCase: UpdateNotifierUseCase,
    @Assisted private val isIncome: Boolean
) : BaseViewModel<TransactionState, TransactionEffect>(TransactionState()) {

    init {
        viewModelScope.launch {
            fetchData()
            updateNotifierUseCase.refreshTrigger.collect {
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
                    startDate = getState().incomeDate,
                    endDate = getState().incomeDate
                )

                transactionResult.fold(
                    onSuccess = { transactions ->
                        val filteredTransactions = transactions.filter { it.category.isIncome == isIncome }
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
                        effect(TransactionEffect.ShowClientError)
                    }
                )
            },
            onFailure = {
                it.printStackTrace()
                effect(TransactionEffect.ShowClientError)
            }
        )

        modifyState { copy(isLoading = false) }
    }

    private fun refresh() {
        viewModelScope.launch {
            fetchData()
        }
    }

    internal fun handleIntent(intent: TransactionIntent) {
        when(intent) {
            TransactionIntent.GoToHistory -> effect(TransactionEffect.NavigateToHistory)
            TransactionIntent.AddTransaction -> effect(TransactionEffect.NavigateToEditorTransaction("-1"))
            TransactionIntent.Refresh -> refresh()
            is TransactionIntent.EditTransaction -> effect(TransactionEffect.NavigateToEditorTransaction(intent.id.toString()))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(isIncome: Boolean): TransactionsViewModel
    }
}