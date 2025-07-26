package com.backcube.settings.vibrate.models

import com.backcube.domain.models.entities.HapticEffect

internal sealed interface SettingVibrateIntent {
    data object GoBack : SettingVibrateIntent
    data class ToggleHaptics(val isActive: Boolean) : SettingVibrateIntent
    data class ChangeHapticsLevel(val effect: HapticEffect) : SettingVibrateIntent
}