package com.backcube.economyapp.core.di

import android.content.Context
import com.backcube.economyapp.App
import com.backcube.economyapp.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
