package com.backcube.economyapp.features.histories

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.features.common.ui.date.DateMode
import com.backcube.economyapp.features.histories.store.models.HistoryEffect
import com.backcube.economyapp.features.histories.store.models.HistoryIntent
import com.backcube.economyapp.features.histories.store.models.HistoryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
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
            try {
                modifyState { copy(isLoading = true) }
                val result = transactionUseCase.getAccountTransactions(
                    accountId = accountUseCase.getAccounts().firstOrNull()?.id ?: 1,
                    startDate = getState().startHistoryDate,
                    endDate = getState().endHistoryDate
                ).filter { it.category.isIncome == getState().isIncome }
                val totalSum = result.sumOf { it.amount }

                modifyState {
                    copy(
                        items = result,
                        totalSum = totalSum
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                effect(HistoryEffect.ShowClientError)
            } finally {
                modifyState { copy(isLoading = false) }
            }
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