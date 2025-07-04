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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class AccountEditViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<AccountEditState, AccountEditEffect>(AccountEditState()) {

    fun fetchData(accountId: Int) {
        viewModelScope.launch {
            try {
                modifyState { copy(isLoading = true) }
                val result = accountUseCase.getAccountById(id = accountId)

                modifyState {
                    if (result != null) {
                        copy(
                            item = result,
                            balance = result.balance.toPlainString()
                        )
                    } else {
                        copy(isLoading = false)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                effect(AccountEditEffect.ShowClientError)
            } finally {
                modifyState { copy(isLoading = false) }
            }
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
            try {
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
                )
                accountNotifierUseCase.notifyAccountChanged()
                effect(AccountEditEffect.GoBack)
            } catch (e: Exception) {
                e.printStackTrace()
                effect(AccountEditEffect.ShowClientError)
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }

    private fun convertAndSaveBalance(balance: String) {
        val newBalance = balance.takeIf { it.isNotBlank() }?.let {
            BigDecimal(it.replace(',', '.'))
        }

        modifyState { copy(item = item?.copy(balance = newBalance ?: BigDecimal.ZERO)) }
    }
}