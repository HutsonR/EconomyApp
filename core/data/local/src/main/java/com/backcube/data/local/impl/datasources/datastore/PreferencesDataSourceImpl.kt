package com.backcube.data.local.impl.datasources.datastore

import com.backcube.data.local.api.datastore.PreferencesDataSource
import com.backcube.data.local.impl.datastore.DataStoreManager
import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.domain.models.entities.ThemeColor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class PreferencesDataSourceImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : PreferencesDataSource {
    override val themeFlow: Flow<AppTheme> = dataStoreManager.themeFlow
    override suspend fun setTheme(theme: AppTheme) = dataStoreManager.setTheme(theme)

    override val colorFlow: Flow<ThemeColor> = dataStoreManager.colorFlow
    override suspend fun setColor(color: ThemeColor) = dataStoreManager.setColor(color)

    override val vibrationEnabledFlow: Flow<Boolean> = dataStoreManager.vibrationEnabledFlow
    override suspend fun setVibrationEnabled(enabled: Boolean) = dataStoreManager.setVibrationEnabled(enabled)

    override val hapticEffectFlow: Flow<HapticEffect> = dataStoreManager.hapticEffectFlow
    override suspend fun setHapticEffect(effect: HapticEffect) = dataStoreManager.setHapticEffect(effect)

    override val localeFlow: Flow<AppLocale> = dataStoreManager.localeFlow
    override suspend fun setLocale(locale: AppLocale) = dataStoreManager.setLocale(locale)
}