package com.backcube.transactions.presentation.analyze

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.transactions.TransactionResponseModel
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.domain.utils.formatAsWholeThousands
import com.backcube.transactions.presentation.analyze.domain.GetCategoriesWithPercentUseCase
import com.backcube.transactions.presentation.analyze.models.AnalyzeEffect
import com.backcube.transactions.presentation.analyze.models.AnalyzeIntent
import com.backcube.transactions.presentation.analyze.models.AnalyzeState
import com.backcube.ui.BaseViewModel
import com.backcube.ui.components.date.DateMode
import com.backcube.ui.utils.toCurrency
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class AnalyzeViewModel @AssistedInject constructor(
    @Assisted private val isIncome: Boolean,
    private val transactionUseCase: TransactionUseCase,
    private val accountUseCase: AccountUseCase,
    private val getCategoriesWithPercentUseCase: GetCategoriesWithPercentUseCase
) : BaseViewModel<AnalyzeState, AnalyzeEffect>(AnalyzeState()) {

    init {
        val now = ZonedDateTime.now()
        val startOfMonth = now
            .withDayOfMonth(1)
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .plusHours(3)
            .toInstant()

        modifyState {
            copy(
                isIncome = this@AnalyzeViewModel.isIncome,
                startAnalyzeDate = startOfMonth
            )
        }
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.Default) {
            modifyState { copy(isLoading = true) }
            accountUseCase.getAccounts().fold(
                onSuccess = { accounts ->
                    val account = accounts.firstOrNull()
                    val accountId = account?.id ?: 1

                    transactionUseCase.getAccountTransactions(
                        accountId = accountId,
                        startDate = getState().startAnalyzeDate,
                        endDate = getState().endAnalyzeDate
                    ).fold(
                        onSuccess = { transactions ->
                            handleTransactionResult(
                                account,
                                transactions
                            )
                        },
                        onFailure = { handleError(it) }
                    )
                },
                onFailure = { handleError(it) }
            )

            modifyState { copy(isLoading = false) }
        }
    }

    private fun handleTransactionResult(
        account: AccountModel?,
        transactions: List<TransactionResponseModel>
    ) {
        val filteredTransactions = transactions.filter {
            it.category.isIncome == getState().isIncome
        }
        val items = getCategoriesWithPercentUseCase(filteredTransactions)
        val currentCurrencyIsoCode = account?.currency ?: CurrencyIsoCode.RUB
        val totalTransactionsSum = filteredTransactions.sumOf { it.amount }
        val formattedTotalSum = totalTransactionsSum.formatAsWholeThousands() + " " + currentCurrencyIsoCode.toCurrency()

        modifyState {
            copy(
                items = items,
                totalSum = formattedTotalSum,
                currencyIsoCode = currentCurrencyIsoCode,
                isLoading = false
            )
        }
    }

    private fun handleError(throwable: Throwable) {
        throwable.printStackTrace()
        effect(AnalyzeEffect.ShowFetchError)
    }

    private fun updateDate(mode: DateMode, newDate: Long?) {
        val newInstant = newDate?.let(Instant::ofEpochMilli) ?: Instant.now()
        var stateChanged = true

        modifyState {
            when (mode) {
                DateMode.START -> {
                    if (newInstant > endAnalyzeDate) {
                        stateChanged = true
                        copy(
                            startAnalyzeDate = newInstant,
                            endAnalyzeDate = newInstant
                        )
                    } else {
                        copy(startAnalyzeDate = newInstant)
                    }
                }
                DateMode.END -> {
                    if (newInstant < startAnalyzeDate) {
                        stateChanged = false
                        this
                    } else {
                        copy(endAnalyzeDate = newInstant)
                    }
                }
            }
        }

        if (stateChanged) fetchData()
    }

    internal fun handleIntent(intent: AnalyzeIntent) {
        when(intent) {
            AnalyzeIntent.GoBack -> effect(AnalyzeEffect.GoBack)
            is AnalyzeIntent.UpdateDate -> updateDate(intent.dateMode, intent.date)
            is AnalyzeIntent.ShowCalendar -> effect(AnalyzeEffect.ShowDatePickerModal(intent.dateMode))
            AnalyzeIntent.Refresh -> fetchData()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(isIncome: Boolean): AnalyzeViewModel
    }
}