package com.backcube.economyapp.features.settings.domain

import com.backcube.economyapp.features.settings.models.SettingType
import com.backcube.economyapp.features.settings.models.SettingsUiModel
import javax.inject.Inject

class GetSettingsListUseCase @Inject constructor() {
    operator fun invoke() = listOf(
            SettingsUiModel(
                name = "Темная тема",
                type = SettingType.SWITCH
            ),
            SettingsUiModel(
                name = "Основной цвет",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "Звуки",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "Хаптики",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "Код пароль",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "Синхронизация",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "Язык",
                type = SettingType.LINK
            ),
            SettingsUiModel(
                name = "О программе",
                type = SettingType.LINK
            )
        )
}