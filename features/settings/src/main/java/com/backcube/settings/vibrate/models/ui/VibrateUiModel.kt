package com.backcube.settings.vibrate.models.ui

import com.backcube.domain.models.entities.HapticEffect

data class VibrateUiModel(
    val hapticEffect: HapticEffect,
    val labelRes: Int,
)