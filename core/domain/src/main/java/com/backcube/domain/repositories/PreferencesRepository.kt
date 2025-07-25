package com.backcube.domain.repositories

import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.models.entities.HapticEffect
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val themeFlow: Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)

    val colorFlow: Flow<Int>
    suspend fun setColor(color: Int)

    val vibrationEnabledFlow: Flow<Boolean>
    suspend fun setVibrationEnabled(enabled: Boolean)

    val hapticEffectFlow: Flow<HapticEffect>
    suspend fun setHapticEffect(effect: HapticEffect)

    val localeFlow: Flow<AppLocale>
    suspend fun setLocale(locale: AppLocale)
}