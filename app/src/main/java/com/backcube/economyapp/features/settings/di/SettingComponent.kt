package com.backcube.economyapp.features.settings.di

import com.backcube.economyapp.features.settings.SettingsViewModel
import dagger.Subcomponent

@Subcomponent
interface SettingComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingComponent
    }

    val settingsViewModel: SettingsViewModel
}