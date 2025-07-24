package com.backcube.settings.common.di

import com.backcube.settings.about.SettingAboutViewModel
import com.backcube.settings.language.SettingLanguageViewModel
import com.backcube.settings.main.SettingsViewModel
import com.backcube.settings.main_color.SettingMainColorViewModel
import com.backcube.settings.vibrate.SettingVibrateViewModel
import dagger.Subcomponent

@Subcomponent
interface SettingComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingComponent
    }

    val settingsViewModel: SettingsViewModel
    val settingMainColorViewModel: SettingMainColorViewModel
    val settingVibrateViewModel: SettingVibrateViewModel
    val settingLanguageViewModel: SettingLanguageViewModel
    val settingAboutViewModel: SettingAboutViewModel
}