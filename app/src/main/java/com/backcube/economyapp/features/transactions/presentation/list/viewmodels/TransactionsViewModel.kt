package com.backcube.economyapp.features.transactions.presentation.list.viewmodels

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.features.transactions.presentation.list.store.models.TransactionEffect
import com.backcube.economyapp.features.transactions.presentation.list.store.models.TransactionIntent
import com.backcube.economyapp.features.transactions.presentation.list.store.models.TransactionState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class TransactionsViewModel @AssistedInject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase,
    @Assisted private val isIncome: Boolean
) : BaseViewModel<TransactionState, TransactionEffect>(TransactionState()) {

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

    fun handleIntent(intent: TransactionIntent) {
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