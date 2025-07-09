package com.backcube.account.main

import androidx.lifecycle.viewModelScope
import com.backcube.account.main.store.models.AccountEffect
import com.backcube.account.main.store.models.AccountIntent
import com.backcube.account.main.store.models.AccountState
import com.backcube.base.BaseViewModel
import com.backcube.domain.models.accounts.AccountUpdateRequestModel
import com.backcube.domain.models.accounts.CurrencyIsoCode
import com.backcube.domain.usecases.api.AccountUseCase
import com.backcube.domain.usecases.impl.common.AccountNotifierUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountUseCase: AccountUseCase,
    private val accountNotifierUseCase: AccountNotifierUseCase
) : BaseViewModel<AccountState, AccountEffect>(AccountState()) {

    init {
        viewModelScope.launch {
            fetchData()
            accountNotifierUseCase.refreshTrigger.collect {
                fetchData()
            }
        }
    }

    private suspend fun fetchData() {
        modifyState { copy(isLoading = true) }

        val accountsResult = accountUseCase.getAccounts()

        accountsResult.fold(
            onSuccess = { accounts ->
                val accountId = accounts.firstOrNull()?.id ?: 1
                val accountResult = accountUseCase.getAccountById(accountId)

                accountResult.fold(
                    onSuccess = { account ->
                        modifyState { copy(item = account) }
                    },
                    onFailure = {
                        it.printStackTrace()
                        effect(AccountEffect.ShowClientError)
                    }
                )
            },
            onFailure = {
                it.printStackTrace()
                effect(AccountEffect.ShowClientError)
            }
        )

        modifyState { copy(isLoading = false) }
    }

    fun handleIntent(intent: AccountIntent) {
        when(intent) {
            is AccountIntent.OnCurrencySelected -> updateAccount(intent.isoCode)
            AccountIntent.OnOpenIsoCodeSheet -> effect(AccountEffect.ShowCurrencySheet)
            is AccountIntent.OnOpenEditScreen -> {
                val accountId = getState().item?.id
                if (accountId == null) {
                    effect(AccountEffect.ShowClientError)
                    return
                }
                effect(AccountEffect.OpenEditScreen(accountId))
            }
        }
    }

    private fun updateAccount(isoCode: CurrencyIsoCode) {
        viewModelScope.launch {
            try {
                modifyState { copy(isLoading = true) }
                val currentAccount = getState().item
                if (currentAccount == null) {
                    effect(AccountEffect.ShowClientError)
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
                )
                accountNotifierUseCase.notifyAccountChanged()
                modifyState { copy(item = updatedAccount) }
            } catch (e: Exception) {
                e.printStackTrace()
                effect(AccountEffect.ShowClientError)
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }
}