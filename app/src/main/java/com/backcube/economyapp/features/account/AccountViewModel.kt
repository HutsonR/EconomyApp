package com.backcube.economyapp.features.account

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.repositories.AccountRepository
import com.backcube.economyapp.features.account.store.models.AccountEffect
import com.backcube.economyapp.features.account.store.models.AccountIntent
import com.backcube.economyapp.features.account.store.models.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : BaseViewModel<AccountState, AccountEffect>(AccountState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                modifyState { copy(isLoading = true) }
                val result = accountRepository.getAccountById(id = 1)

                modifyState {
                    if (result != null) {
                        copy(item = result)
                    } else {
                        copy(
                            hasError = true,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }

    fun handleIntent(intent: AccountIntent) {
        // todo Дальше больше
        when(intent) {
            else -> Unit
        }
    }
}