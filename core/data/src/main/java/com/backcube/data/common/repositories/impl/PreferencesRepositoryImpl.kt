package com.backcube.data.common.repositories.impl

import com.backcube.data.local.api.datastore.PreferencesDataSource
import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.domain.models.entities.ThemeColor
import com.backcube.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : PreferencesRepository {
    override val themeFlow: Flow<AppTheme> = preferencesDataSource.themeFlow
    override suspend fun setTheme(theme: AppTheme) = preferencesDataSource.setTheme(theme)

    override val colorFlow: Flow<ThemeColor> = preferencesDataSource.colorFlow
    override suspend fun setColor(color: ThemeColor) = preferencesDataSource.setColor(color)

    override val vibrationEnabledFlow: Flow<Boolean> = preferencesDataSource.vibrationEnabledFlow
    override suspend fun setVibrationEnabled(enabled: Boolean) = preferencesDataSource.setVibrationEnabled(enabled)

    override val hapticEffectFlow: Flow<HapticEffect> = preferencesDataSource.hapticEffectFlow
    override suspend fun setHapticEffect(effect: HapticEffect) = preferencesDataSource.setHapticEffect(effect)

    override val localeFlow: Flow<AppLocale> = preferencesDataSource.localeFlow
    override suspend fun setLocale(locale: AppLocale) = preferencesDataSource.setLocale(locale)
}