package com.backcube.settings.di

import com.backcube.settings.SettingsViewModel
import dagger.Subcomponent

@Subcomponent
interface SettingComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingComponent
    }

    val settingsViewModel: SettingsViewModel
}