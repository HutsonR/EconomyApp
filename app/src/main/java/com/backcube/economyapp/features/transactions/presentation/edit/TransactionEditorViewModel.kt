package com.backcube.economyapp.features.transactions.presentation.edit

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.models.accounts.AccountModel
import com.backcube.economyapp.domain.models.categories.CategoryModel
import com.backcube.economyapp.domain.models.transactions.TransactionRequestModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.api.CategoryUseCase
import com.backcube.economyapp.domain.usecases.api.TransactionUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorEffect
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorIntent
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.Instant

class TransactionEditorViewModel @AssistedInject constructor(
    @Assisted private val transactionId: Int,
    private val transactionUseCase: TransactionUseCase,
    private val categoryUseCase: CategoryUseCase,
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<TransactionEditorState, TransactionEditorEffect>(TransactionEditorState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch(Dispatchers.Default) {
            modifyState { copy(isLoading = true) }

            val accountsDeferred = async { accountUseCase.getAccounts() }
            val categoriesDeferred = async { categoryUseCase.getCategories() }

            val accountsResult = accountsDeferred.await()
            val categoriesResult = categoriesDeferred.await()

            accountsResult.onSuccess { accounts ->
                modifyState { copy(accounts = accounts) }
            }.onFailure {
                it.printStackTrace()
                effect(TransactionEditorEffect.ShowFetchError)
            }

            categoriesResult.onSuccess { categories ->
                modifyState { copy(categories = categories) }
            }.onFailure {
                it.printStackTrace()
                effect(TransactionEditorEffect.ShowFetchError)
            }

            if (transactionId != TRANSACTION_NOT_FOUND) {
                val transactionResult = transactionUseCase.getTransactionById(transactionId)

                transactionResult.onSuccess { transaction ->
                    modifyState { copy(item = transaction) }
                }.onFailure {
                    it.printStackTrace()
                    effect(TransactionEditorEffect.ShowFetchError)
                }
            }

            modifyState { copy(isLoading = false) }
        }
    }

    fun handleIntent(intent: TransactionEditorIntent) {
        when (intent) {
            is TransactionEditorIntent.OnAccountSelected -> updateTransactionAccount(intent.account)
            is TransactionEditorIntent.OnAmountChange -> updateTransactionAmount(intent.amount)
            is TransactionEditorIntent.OnCategorySelected -> updateTransactionCategory(intent.category)
            is TransactionEditorIntent.OnDateSelected -> updateDate(intent.date)
            is TransactionEditorIntent.OnDescriptionChange -> updateTransactionDescription(intent.description)
            TransactionEditorIntent.OnOpenAccountSheet -> effect(TransactionEditorEffect.ShowAccountSheet)
            TransactionEditorIntent.OnOpenCategorySheet -> effect(TransactionEditorEffect.ShowCategorySheet)
            TransactionEditorIntent.OnOpenDatePickerModal -> effect(TransactionEditorEffect.ShowDatePickerModal)
            TransactionEditorIntent.OnCancelClick -> effect(TransactionEditorEffect.GoBack)
            TransactionEditorIntent.OnSaveClick -> updateTransaction()
            TransactionEditorIntent.Refresh -> fetchData()
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

    private fun updateTransaction() {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }
            convertAndSaveBalance(getState().amount)

            val selectedAccountId = getState().selectedAccount?.id
            val selectedCategoryId = getState().selectedCategory?.id
            val selectedTransactionDate = getState().selectedTransactionDate
            if (selectedAccountId == null || selectedCategoryId == null) {
                effect(TransactionEditorEffect.ShowClientError)
                return@launch
            }

            val requestTransactionModel = TransactionRequestModel(
                accountId = selectedAccountId,
                categoryId = selectedCategoryId,
                amount = getState().formattedAmount,
                transactionDate = selectedTransactionDate,
                comment = getState().comment,
            )

            val result = if (transactionId == TRANSACTION_NOT_FOUND) {
                transactionUseCase.createTransaction(requestTransactionModel)
            } else {
                transactionUseCase.updateTransaction(transactionId, requestTransactionModel)
            }

            result.fold(
                onSuccess = {
                    effect(TransactionEditorEffect.GoBack)
                },
                onFailure = {
                    it.printStackTrace()
                    modifyState { copy(isLoading = false) }
                    effect(TransactionEditorEffect.GoBack)
                }
            )
            accountNotifierUseCase.notifyAccountChanged()
        }
    }

    private fun convertAndSaveBalance(balance: String) {
        val newBalance = balance.takeIf { it.isNotBlank() }?.let {
            BigDecimal(it.replace(',', '.'))
        }
        modifyState { copy(formattedAmount = newBalance ?: BigDecimal.ZERO) }
    }

    @AssistedFactory
    interface Factory {
        fun create(transactionId: Int): TransactionEditorViewModel
    }

    companion object {
        private const val TRANSACTION_NOT_FOUND = -1
    }

}