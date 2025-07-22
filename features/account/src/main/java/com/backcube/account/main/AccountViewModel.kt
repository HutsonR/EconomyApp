package com.backcube.account.main

import androidx.lifecycle.viewModelScope
import com.backcube.account.main.models.AccountEffect
import com.backcube.account.main.models.AccountIntent
import com.backcube.account.main.models.AccountState
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.impl.common.UpdateNotifierUseCase
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.domain.utils.NoInternetConnectionException
import com.backcube.ui.BaseViewModel
import com.backcube.ui.components.AlertData
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val updateNotifierUseCase: UpdateNotifierUseCase
) : BaseViewModel<AccountState, AccountEffect>(AccountState()) {

    init {
        viewModelScope.launch {
            fetchData()
            updateNotifierUseCase.refreshTrigger.collect {
                fetchData()
            }
        }
    }

    private suspend fun fetchData() {
        modifyState { copy(isLoading = true) }

       accountUseCase.getAccounts().fold (
           onSuccess = { accounts ->
               val accountId = accounts.firstOrNull()?.id ?: 1
               accountUseCase.getAccountById(accountId).fold(
                   onSuccess = { account ->
                       modifyState {
                           copy(
                               item = account,
                               isLoading = false
                           )
                       }
                   },
                   onFailure = { handleError(it) }
               )
           },
           onFailure = { handleError(it) }
       )

        modifyState { copy(isLoading = false) }
    }

    internal fun handleIntent(intent: AccountIntent) {
        when(intent) {
            is AccountIntent.OnCurrencySelected -> updateAccount(intent.isoCode)
            AccountIntent.OnOpenIsoCodeSheet -> effect(AccountEffect.ShowCurrencySheet)
            is AccountIntent.OnOpenEditScreen -> {
                val accountId = getState().item?.id
                if (accountId == null) {
                    effect(AccountEffect.ShowError())
                    return
                }
                effect(AccountEffect.OpenEditScreen(accountId))
            }
        }
    }

    private fun updateAccount(isoCode: CurrencyIsoCode) {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

            val currentAccount = getState().item
            if (currentAccount == null) {
                effect(AccountEffect.ShowError())
                return@launch
            }
            val updatedAccount = currentAccount.copy(currency = isoCode)

            accountUseCase.updateAccount(
                currentAccount.id,
                request = AccountUpdateRequestModel(
                    name = updatedAccount.name,
                    balance = updatedAccount.balance,
                    currency = updatedAccount.currency
                )
            ).fold(
                onSuccess = {
                    updateNotifierUseCase.notifyDataChanged()
                    modifyState {
                        copy(
                            item = updatedAccount,
                            isLoading = false
                        )
                    }
                },
                onFailure = { handleError(it) }
            )

            modifyState { copy(isLoading = false) }
        }
    }

    private fun handleError(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is NoInternetConnectionException -> {
                effect(
                    AccountEffect.ShowError(
                        AlertData(message = com.backcube.ui.R.string.no_internet_connection)
                    )
                )
            }
            else -> effect(AccountEffect.ShowError())
        }
    }
}