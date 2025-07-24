package com.backcube.settings.main_color.models

internal sealed interface SettingMainColorIntent {
    data object GoBack : SettingMainColorIntent
}