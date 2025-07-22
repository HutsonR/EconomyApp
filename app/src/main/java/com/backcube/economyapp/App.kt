package com.backcube.economyapp

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import com.backcube.account.common.di.AccountComponent
import com.backcube.account.common.di.AccountComponentProvider
import com.backcube.articles.di.ArticlesComponent
import com.backcube.articles.di.ArticlesComponentProvider
import com.backcube.economyapp.workmanager.SyncWorker
import com.backcube.settings.di.SettingComponent
import com.backcube.settings.di.SettingComponentProvider
import com.backcube.transactions.common.di.TransactionsComponent
import com.backcube.transactions.common.di.TransactionsComponentProvider

class App: Application(), AccountComponentProvider, ArticlesComponentProvider, SettingComponentProvider, TransactionsComponentProvider {

    lateinit var appComponent: AppComponent
        private set

    private val _accountComponent: AccountComponent by lazy {
        appComponent.createAccountComponent().create()
    }

    private val _articlesComponent: ArticlesComponent by lazy {
        appComponent.createArticlesComponent().create()
    }

    private val _settingComponent: SettingComponent by lazy {
        appComponent.createSettingsComponent().create()
    }

    private val _transactionComponent: TransactionsComponent by lazy {
        appComponent.createTransactionsComponent().create()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .setContext(applicationContext)
            .build()

        val customWorkerFactory = appComponent.createCustomWorkerFactory()

        val workManagerConfig = Configuration.Builder()
            .setWorkerFactory(customWorkerFactory)
            .build()

        WorkManager.initialize(this, workManagerConfig)
        SyncWorker.setupPeriodicSync(this)
    }

    override fun provideAccountComponent(): AccountComponent = _accountComponent
    override fun provideArticlesComponent(): ArticlesComponent = _articlesComponent
    override fun provideSettingComponent(): SettingComponent = _settingComponent
    override fun provideTransactionsComponent(): TransactionsComponent = _transactionComponent

    companion object {
        val Context.appComponent: AppComponent
            get() = (applicationContext as App).appComponent
    }
}