package com.backcube.data.local.impl.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.backcube.domain.models.entities.AppLocale
import com.backcube.domain.models.entities.AppTheme
import com.backcube.domain.models.entities.HapticEffect
import com.backcube.domain.models.entities.ThemeColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("finance_blackcube_app_prefs")

@Singleton
internal class DataStoreManager @Inject constructor(
    private val context: Context
) {
    companion object {
        private val THEME_KEY = stringPreferencesKey("theme_key")
        private val COLOR_KEY = stringPreferencesKey("color_key")
        private val VIBRATION_ENABLED_KEY = booleanPreferencesKey("vibration_enabled_key")
        private val HAPTIC_EFFECT_KEY = stringPreferencesKey("haptic_effect_key")
        private val LOCALE_KEY = stringPreferencesKey("locale_key")
    }

    val themeFlow: Flow<AppTheme> = context.dataStore.data
        .map { prefs ->
            prefs[THEME_KEY]?.let { AppTheme.valueOf(it) } ?: AppTheme.LIGHT
        }
    suspend fun setTheme(theme: AppTheme) {
        context.dataStore.edit { prefs -> prefs[THEME_KEY] = theme.name }
    }

    val colorFlow: Flow<ThemeColor> = context.dataStore.data
        .map { prefs ->
            safeValueOf(prefs[COLOR_KEY], ThemeColor.GREEN)
        }
    suspend fun setColor(color: ThemeColor) {
        context.dataStore.edit { prefs -> prefs[COLOR_KEY] = color.name }
    }

    val vibrationEnabledFlow: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[VIBRATION_ENABLED_KEY] == true }
    suspend fun setVibrationEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[VIBRATION_ENABLED_KEY] = enabled }
    }

    val hapticEffectFlow: Flow<HapticEffect> = context.dataStore.data
        .map { prefs ->
            safeValueOf(prefs[HAPTIC_EFFECT_KEY], HapticEffect.MEDIUM)
        }
    suspend fun setHapticEffect(effect: HapticEffect) {
        context.dataStore.edit { prefs -> prefs[HAPTIC_EFFECT_KEY] = effect.name }
    }

    val localeFlow: Flow<AppLocale> = context.dataStore.data
        .map { prefs ->
            safeValueOf(prefs[LOCALE_KEY], AppLocale.RU)
        }
    suspend fun setLocale(locale: AppLocale) {
        context.dataStore.edit { prefs -> prefs[LOCALE_KEY] = locale.name }
    }

    private inline fun <reified T : Enum<T>> safeValueOf(name: String?, default: T): T {
        return enumValues<T>()
            .firstOrNull { it.name.equals(name, ignoreCase = true) }
            ?: default
    }
}