package com.backcube.data.local.api.datastore

import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.domain.models.entities.ThemeColor
import kotlinx.coroutines.flow.Flow

interface PreferencesDataSource {
    val themeFlow: Flow<AppTheme>
    suspend fun setTheme(theme: AppTheme)

    val colorFlow: Flow<ThemeColor>
    suspend fun setColor(color: ThemeColor)

    val vibrationEnabledFlow: Flow<Boolean>
    suspend fun setVibrationEnabled(enabled: Boolean)

    val hapticEffectFlow: Flow<HapticEffect>
    suspend fun setHapticEffect(effect: HapticEffect)

    val localeFlow: Flow<AppLocale>
    suspend fun setLocale(locale: AppLocale)
}