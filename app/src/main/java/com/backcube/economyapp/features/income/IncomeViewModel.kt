package com.backcube.economyapp.features.income

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.repositories.TransactionRepository
import com.backcube.economyapp.features.income.store.models.IncomeEffect
import com.backcube.economyapp.features.income.store.models.IncomeIntent
import com.backcube.economyapp.features.income.store.models.IncomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository
) : BaseViewModel<IncomeState, IncomeEffect>(IncomeState()) {

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
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }

    fun handleIntent(intent: IncomeIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }
}