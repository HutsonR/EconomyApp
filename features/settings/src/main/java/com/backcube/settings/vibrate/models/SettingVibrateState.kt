package com.backcube.settings.vibrate.models

import com.backcube.domain.models.entities.HapticEffect
import com.backcube.settings.vibrate.models.ui.VibrateUiModel

data class SettingVibrateState(
    val isHapticsEnabled: Boolean = false,
    val currentHapticEffect: HapticEffect = HapticEffect.MEDIUM,
    val hapticAvailableEffects: List<VibrateUiModel> = emptyList()
)
