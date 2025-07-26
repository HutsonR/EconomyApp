package com.backcube.settings.vibrate.mappers

import androidx.annotation.StringRes
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.settings.R

@StringRes
fun HapticEffect.labelRes(): Int = when (this) {
    HapticEffect.LIGHT -> R.string.settings_vibrate_easy
    HapticEffect.MEDIUM -> R.string.settings_vibrate_medium
    HapticEffect.HEAVY -> R.string.settings_vibrate_hard
}