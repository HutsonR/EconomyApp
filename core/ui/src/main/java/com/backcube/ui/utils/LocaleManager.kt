package com.backcube.ui.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleManager {
    fun updateContextLocale(context: Context, localeCode: String): Context {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        config.setLayoutDirection(locale)

        return context.createConfigurationContext(config)
    }
}
