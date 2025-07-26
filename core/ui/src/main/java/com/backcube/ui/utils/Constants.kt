package com.backcube.ui.utils

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppContext = staticCompositionLocalOf<Context> {
    error("No context provided")
}