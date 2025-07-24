package com.backcube.settings.domain

import com.backcube.settings.models.ui.SettingType
import com.backcube.settings.models.ui.SettingsUiModel
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