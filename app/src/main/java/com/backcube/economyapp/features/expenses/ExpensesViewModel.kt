package com.backcube.economyapp.features.expenses

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import com.backcube.economyapp.features.expenses.store.models.ExpenseEffect
import com.backcube.economyapp.features.expenses.store.models.ExpenseIntent
import com.backcube.economyapp.features.expenses.store.models.ExpenseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel<ExpenseState, ExpenseEffect>(ExpenseState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                modifyState { copy(isLoading = true) }
                val result = transactionRepository.getAccountTransactions(
                    accountId = 1,
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
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }

    fun handleIntent(intent: ExpenseIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }
}