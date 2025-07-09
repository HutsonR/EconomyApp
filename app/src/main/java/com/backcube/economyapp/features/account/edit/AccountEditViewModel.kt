package com.backcube.economyapp.features.account.edit

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.economyapp.domain.usecases.api.AccountUseCase
import com.backcube.economyapp.domain.usecases.impl.common.AccountNotifierUseCase
import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import com.backcube.economyapp.features.account.edit.store.models.AccountEditEffect
import com.backcube.economyapp.features.account.edit.store.models.AccountEditIntent
import com.backcube.economyapp.features.account.edit.store.models.AccountEditState
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

class AccountEditViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<AccountEditState, AccountEditEffect>(AccountEditState()) {

    fun fetchData(accountId: Int) {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }
            val accountResult = accountUseCase.getAccountById(accountId)

            accountResult.fold(
                onSuccess = { account ->
                    modifyState {
                        if (account != null) {
                            copy(
                                item = account,
                                balance = account.balance.toPlainString()
                            )
                        } else {
                            copy(isLoading = false)
                        }
                    }
                },
                onFailure = {
                    it.printStackTrace()
                    effect(AccountEditEffect.ShowClientError)
                }
            )

            modifyState { copy(isLoading = false) }
        }
    }

    fun handleIntent(intent: AccountEditIntent) {
        when(intent) {
            is AccountEditIntent.OnAccountBalanceChange -> updateAccountBalance(intent.balance)
            is AccountEditIntent.OnAccountNameChange -> updateAccountName(intent.name)
            is AccountEditIntent.OnCurrencySelected -> updateCurrencyIsoCode(intent.isoCode)
            AccountEditIntent.OnOpenIsoCodeSheet -> effect(AccountEditEffect.ShowCurrencySheet)
            AccountEditIntent.OnSaveClick -> updateAccount()
            AccountEditIntent.OnCancelClick -> effect(AccountEditEffect.GoBack)
        }
    }

    private fun updateAccountName(name: String) {
        modifyState { copy(item = item?.copy(name = name)) }
    }

    private fun updateAccountBalance(balance: String) {
        modifyState { copy(balance = balance) }
    }

    private fun updateCurrencyIsoCode(isoCode: CurrencyIsoCode) {
        modifyState { copy(item = item?.copy(currency = isoCode)) }
    }

    private fun updateAccount() {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

            convertAndSaveBalance(getState().balance)
            val updatedAccount = getState().item

            if (updatedAccount == null) {
                effect(AccountEditEffect.ShowClientError)
                return@launch
            }

            accountUseCase.updateAccount(
                updatedAccount.id,
                request = AccountUpdateRequestModel(
                    name = updatedAccount.name,
                    balance = updatedAccount.balance,
                    currency = updatedAccount.currency
                )
            ).fold(
                onSuccess = {
                    accountNotifierUseCase.notifyAccountChanged()
                    effect(AccountEditEffect.GoBack)
                },
                onFailure = {
                    it.printStackTrace()
                    modifyState { copy(isLoading = false) }
                    effect(AccountEditEffect.ShowClientError)
                }
            )
        }
    }

    private fun convertAndSaveBalance(balance: String) {
        val newBalance = balance.takeIf { it.isNotBlank() }?.let {
            BigDecimal(it.replace(',', '.'))
        }

        modifyState { copy(item = item?.copy(balance = newBalance ?: BigDecimal.ZERO)) }
    }
}