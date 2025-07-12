package com.backcube.economyapp

import android.app.Application

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .setContext(applicationContext)
            .build()
    }
}