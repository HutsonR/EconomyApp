package com.backcube.account.common.di

import com.backcube.account.edit.AccountEditViewModel
import com.backcube.account.main.AccountViewModel
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
