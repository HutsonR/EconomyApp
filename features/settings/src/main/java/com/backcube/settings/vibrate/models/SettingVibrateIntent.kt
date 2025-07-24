package com.backcube.settings.vibrate.models

internal sealed interface SettingVibrateIntent {
    data object GoBack : SettingVibrateIntent
}