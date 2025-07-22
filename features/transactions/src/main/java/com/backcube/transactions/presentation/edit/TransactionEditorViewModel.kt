package com.backcube.transactions.presentation.edit

import androidx.lifecycle.viewModelScope
import com.backcube.domain.models.accounts.AccountModel
import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.models.transactions.TransactionRequestModel
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.api.CategoryUseCase
import com.backcube.domain.usecases.api.TransactionUseCase
import com.backcube.domain.usecases.impl.common.UpdateNotifierUseCase
import com.backcube.domain.utils.NoInternetConnectionException
import com.backcube.transactions.presentation.edit.models.TransactionEditorEffect
import com.backcube.transactions.presentation.edit.models.TransactionEditorIntent
import com.backcube.transactions.presentation.edit.models.TransactionEditorState
import com.backcube.ui.BaseViewModel
import com.backcube.ui.components.AlertData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId

class TransactionEditorViewModel @AssistedInject constructor(
    @Assisted private val transactionId: Int,
    @Assisted private val isIncome: Boolean,
    private val transactionUseCase: TransactionUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val accountUseCase: AccountUseCase,
    private val updateNotifierUseCase: UpdateNotifierUseCase
) : BaseViewModel<TransactionEditorState, TransactionEditorEffect>(TransactionEditorState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.Default) {
            modifyState {
                copy(
                    isLoading = true,
                    isEditMode = transactionId != TRANSACTION_NOT_EXIST
                )
            }

            val accountsDeferred = async { accountUseCase.getAccounts() }
            val categoriesDeferred = async { categoryUseCase.getCategories() }

            val accountsResult = accountsDeferred.await()
            val categoriesResult = categoriesDeferred.await()

            accountsResult.fold(
                onSuccess = { accounts ->
                    modifyState { copy(accounts = accounts) }
                },
                onFailure = {
                    it.printStackTrace()
                    effect(TransactionEditorEffect.ShowFetchError)
                }
            )

            categoriesResult.fold(
                onSuccess = { categories ->
                    val filteredCategories = categories.filter { it.isIncome == isIncome }
                    modifyState { copy(categories = filteredCategories) }
                },
                onFailure = {
                    it.printStackTrace()
                    effect(TransactionEditorEffect.ShowFetchError)
                }
            )

            if (transactionId != TRANSACTION_NOT_EXIST) {
                transactionUseCase.getTransactionById(transactionId).fold(
                    onSuccess = { transaction ->
                        val selectedAccount = getState().accounts.singleOrNull { it.id == transaction.account.id }
                        modifyState {
                            copy(
                                selectedAccount = selectedAccount,
                                selectedCategory = transaction.category,
                                amount = transaction.amount.toString(),
                                selectedTransactionDate = transaction.transactionDate,
                                comment = transaction.comment.orEmpty(),
                                isLoading = false
                            )
                        }
                    },
                    onFailure = {
                        it.printStackTrace()
                        effect(TransactionEditorEffect.ShowFetchError)
                    }
                )
            }

            modifyState { copy(isLoading = false) }
        }
    }

    internal fun handleIntent(intent: TransactionEditorIntent) {
        when (intent) {
            is TransactionEditorIntent.OnAccountSelected -> updateTransactionAccount(intent.account)
            is TransactionEditorIntent.OnAmountChange -> updateTransactionAmount(intent.amount)
            is TransactionEditorIntent.OnCategorySelected -> updateTransactionCategory(intent.category)
            is TransactionEditorIntent.OnDateSelected -> updateDate(intent.date)
            is TransactionEditorIntent.OnTimeSelected -> updateTime(intent.hour, intent.minute)
            is TransactionEditorIntent.OnDescriptionChange -> updateTransactionDescription(intent.description)
            TransactionEditorIntent.OnOpenAccountSheet -> effect(TransactionEditorEffect.ShowAccountSheet)
            TransactionEditorIntent.OnOpenCategorySheet -> effect(TransactionEditorEffect.ShowCategorySheet)
            TransactionEditorIntent.OnOpenDatePickerModal -> effect(TransactionEditorEffect.ShowDatePickerModal)
            TransactionEditorIntent.OnOpenTimePickerModal -> effect(TransactionEditorEffect.ShowTimePickerModal)
            TransactionEditorIntent.OnCancelClick -> effect(TransactionEditorEffect.GoBack)
            TransactionEditorIntent.OnSaveClick -> updateTransaction()
            TransactionEditorIntent.Refresh -> fetchData()
            TransactionEditorIntent.OnDeleteClick -> deleteTransaction()
        }
    }

    private fun updateTransactionDescription(description: String) {
        modifyState { copy(comment = description) }
    }

    private fun updateTransactionAccount(account: AccountModel) {
        modifyState { copy(selectedAccount = account) }
    }

    private fun updateTransactionAmount(amount: String) {
        modifyState { copy(amount = amount) }
    }

    private fun updateTransactionCategory(category: CategoryModel) {
        modifyState { copy(selectedCategory = category) }
    }

    private fun updateDate(newDate: Long?) {
        val newInstant = newDate?.let { Instant.ofEpochMilli(it) } ?: Instant.now()
        modifyState { copy(selectedTransactionDate = newInstant) }
    }

    private fun updateTime(hour: Int, minute: Int) {
        val oldDate = getState().selectedTransactionDate.atZone(ZoneId.systemDefault())
        val newDateTime = oldDate
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        modifyState { copy(selectedTransactionDate = newDateTime.toInstant()) }
    }

    private fun deleteTransaction() {
        viewModelScope.launch {
            transactionUseCase.deleteTransaction(transactionId).fold(
                onSuccess = {
                    if (it) {
                        updateNotifierUseCase.notifyDataChanged()
                        effect(TransactionEditorEffect.GoBack)
                    } else {
                        effect(TransactionEditorEffect.ShowClientError())
                    }
                },
                onFailure = {
                    handleClientError(it)
                }
            )
        }
    }

    private fun updateTransaction() {
        viewModelScope.launch {
            val selectedAccountId = getState().selectedAccount?.id
            val selectedCategoryId = getState().selectedCategory?.id
            val selectedTransactionDate = getState().selectedTransactionDate
            val amount = getState().amount
            if (selectedAccountId == null || selectedCategoryId == null || amount.isBlank()) {
                effect(
                    TransactionEditorEffect.ShowClientError(
                        AlertData(message = com.backcube.ui.R.string.alert_reqiured_fields_description)
                    )
                )
                return@launch
            }

            modifyState { copy(isLoading = true) }

            val requestTransactionModel = TransactionRequestModel(
                accountId = selectedAccountId,
                categoryId = selectedCategoryId,
                amount = toBigDecimal(amount),
                transactionDate = selectedTransactionDate,
                comment = getState().comment,
            )

            val result = if (transactionId == TRANSACTION_NOT_EXIST) {
                transactionUseCase.createTransaction(requestTransactionModel)
            } else {
                transactionUseCase.updateTransaction(transactionId, requestTransactionModel)
            }

            result.fold(
                onSuccess = {
                    updateNotifierUseCase.notifyDataChanged()
                    effect(TransactionEditorEffect.GoBack)
                },
                onFailure = {
                    modifyState { copy(isLoading = false) }
                    handleClientError(it)
                }
            )
        }
    }

    private fun toBigDecimal(balance: String): BigDecimal {
        return balance.takeIf { it.isNotBlank() }?.let {
            BigDecimal(it.replace(',', '.'))
        } ?: BigDecimal.ZERO
    }

    private fun handleClientError(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is NoInternetConnectionException -> {
                effect(
                    TransactionEditorEffect.ShowClientError(
                        AlertData(message = com.backcube.ui.R.string.no_internet_connection)
                    )
                )
            }
            else -> effect(TransactionEditorEffect.ShowClientError())
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(transactionId: Int, isIncome: Boolean): TransactionEditorViewModel
    }

    companion object {
        private const val TRANSACTION_NOT_EXIST = -1
    }

}