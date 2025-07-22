package com.backcube.transactions.presentation.histories

import androidx.lifecycle.viewModelScope
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.transactions.presentation.histories.models.HistoryEffect
import com.backcube.transactions.presentation.histories.models.HistoryEffect.GoBack
import com.backcube.transactions.presentation.histories.models.HistoryEffect.NavigateToAnalyze
import com.backcube.transactions.presentation.histories.models.HistoryEffect.NavigateToEditorTransaction
import com.backcube.transactions.presentation.histories.models.HistoryEffect.ShowCalendar
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
            .plusHours(3)
            .toInstant()

        modifyState { copy(startHistoryDate = startOfMonth) }
    }

    private fun fetchData() {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }
            accountUseCase.getAccounts().fold(
                onSuccess = { accounts ->
                    val accountId = accounts.firstOrNull()?.id ?: 1

                    transactionUseCase.getAccountTransactions(
                        accountId = accountId,
                        startDate = getState().startHistoryDate,
                        endDate = getState().endHistoryDate
                    ).fold(
                        onSuccess = { transactions ->
                            val filteredTransactions = transactions.filter {
                                it.category.isIncome == getState().isIncome
                            }
                            val totalTransactionsSum = filteredTransactions.sumOf { it.amount }

                            modifyState {
                                copy(
                                    items = filteredTransactions,
                                    totalSum = totalTransactionsSum,
                                    isLoading = false
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
        val newInstant = newDate?.let(Instant::ofEpochMilli) ?: Instant.now()
        var stateChanged = true

        modifyState {
            when (mode) {
                DateMode.START -> {
                    if (newInstant > endHistoryDate) {
                        stateChanged = true
                        copy(
                            startHistoryDate = newInstant,
                            endHistoryDate = newInstant
                        )
                    } else {
                        copy(startHistoryDate = newInstant)
                    }
                }
                DateMode.END -> {
                    if (newInstant < startHistoryDate) {
                        stateChanged = false
                        this
                    } else {
                        copy(endHistoryDate = newInstant)
                    }
                }
            }
        }

        if (stateChanged) fetchData()
    }


    internal fun handleIntent(intent: HistoryIntent) {
        when(intent) {
            HistoryIntent.GoBack -> effect(GoBack)
            is HistoryIntent.UpdateDate -> updateDate(intent.dateMode, intent.date)
            is HistoryIntent.ShowCalendar -> effect(ShowCalendar(intent.dateMode))
            is HistoryIntent.EditTransaction -> effect(NavigateToEditorTransaction(intent.id.toString()))
            is HistoryIntent.GoAnalyze -> effect(NavigateToAnalyze(intent.isIncome))
        }
    }
}