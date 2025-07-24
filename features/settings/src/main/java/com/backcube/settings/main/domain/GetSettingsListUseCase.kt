package com.backcube.settings.main.domain

import com.backcube.settings.R
import com.backcube.settings.main.models.ui.SettingItemId
import com.backcube.settings.main.models.ui.SettingType
import com.backcube.settings.main.models.ui.SettingsUiModel
import javax.inject.Inject

class GetSettingsListUseCase @Inject constructor() {
    operator fun invoke() = listOf(
        SettingsUiModel(R.string.settings_dark_theme, SettingItemId.DARK_THEME, SettingType.SWITCH),
        SettingsUiModel(R.string.settings_main_color, SettingItemId.COLOR, SettingType.LINK),
        SettingsUiModel(R.string.settings_vibrate, SettingItemId.HAPTICS, SettingType.LINK),
        SettingsUiModel(R.string.settings_password, SettingItemId.PASSCODE, SettingType.LINK),
        SettingsUiModel(R.string.settings_sync, SettingItemId.SYNC, SettingType.LINK),
        SettingsUiModel(R.string.settings_language, SettingItemId.LANGUAGE, SettingType.LINK),
        SettingsUiModel(R.string.settings_about, SettingItemId.ABOUT, SettingType.LINK)
    )
}