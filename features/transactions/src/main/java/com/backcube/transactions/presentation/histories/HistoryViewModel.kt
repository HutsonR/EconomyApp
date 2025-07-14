package com.backcube.transactions.presentation.histories

import androidx.lifecycle.viewModelScope
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.transactions.presentation.histories.models.HistoryEffect
import com.backcube.transactions.presentation.histories.models.HistoryIntent
import com.backcube.transactions.presentation.histories.models.HistoryState
import com.backcube.ui.BaseViewModel
import com.backcube.ui.components.date.DateMode
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase
) : BaseViewModel<HistoryState, HistoryEffect>(HistoryState()) {

    init {
        val now = ZonedDateTime.now()
        val startOfMonth = now
            .withDayOfMonth(1)
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()

        modifyState { copy(startHistoryDate = startOfMonth) }
    }

    private fun fetchData() {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }
            val accountsResult = accountUseCase.getAccounts()

            accountsResult.fold(
                onSuccess = { accounts ->
                    val accountId = accounts.firstOrNull()?.id ?: 1
                    val transactionResult = transactionUseCase.getAccountTransactions(
                        accountId = accountId,
                        startDate = getState().startHistoryDate,
                        endDate = getState().endHistoryDate
                    )

                    transactionResult.fold(
                        onSuccess = { transactions ->
                            val filteredTransactions = transactions.filter {
                                it.category.isIncome == getState().isIncome
                            }
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
                            effect(HistoryEffect.ShowClientError)
                        }
                    )
                },
                onFailure = {
                    it.printStackTrace()
                    effect(HistoryEffect.ShowClientError)
                }
            )

            modifyState { copy(isLoading = false) }
        }
    }

    fun setIncomeFlag(isIncome: Boolean) {
        modifyState { copy(isIncome = isIncome) }
        fetchData()
    }

    private fun updateDate(mode: DateMode, newDate: Long?) {
        val newInstant = newDate?.let { Instant.ofEpochMilli(it) } ?: Instant.now()

        modifyState {
            when (mode) {
                DateMode.START -> copy(startHistoryDate = newInstant)
                DateMode.END -> {
                    if (newInstant != null && newInstant < startHistoryDate) {
                        // Нельзя выбрать дату раньше начала
                        return@modifyState this
                    }
                    copy(endHistoryDate = newInstant)
                }
            }
        }
        fetchData()
    }


    fun handleIntent(intent: HistoryIntent) {
        when(intent) {
            HistoryIntent.GoBack -> effect(HistoryEffect.GoBack)
            is HistoryIntent.UpdateDate -> updateDate(intent.dateMode, intent.date)
            is HistoryIntent.ShowCalendar -> effect(HistoryEffect.ShowCalendar(intent.dateMode))
        }
    }
}