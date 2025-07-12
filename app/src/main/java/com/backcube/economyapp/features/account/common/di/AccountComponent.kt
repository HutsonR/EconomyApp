package com.backcube.economyapp.features.account.common.di

import com.backcube.economyapp.features.account.edit.AccountEditViewModel
import com.backcube.economyapp.features.account.main.AccountViewModel
import dagger.Subcomponent

@Subcomponent
interface AccountComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): AccountComponent
    }

    val accountViewModel: AccountViewModel
    val accountEditViewModel: AccountEditViewModel
}
