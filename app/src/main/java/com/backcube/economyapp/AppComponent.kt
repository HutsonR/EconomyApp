package com.backcube.economyapp

import android.content.Context
import com.backcube.economyapp.data.di.DataModule
import com.backcube.economyapp.domain.di.DomainModule
import com.backcube.economyapp.features.account.common.di.AccountComponent
import com.backcube.economyapp.features.articles.di.ArticlesComponent
import com.backcube.economyapp.features.settings.di.SettingComponent
import com.backcube.economyapp.features.transactions.common.di.TransactionsComponent
import com.backcube.economyapp.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        DomainModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setContext(context: Context): Builder
        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun createAccountComponent(): AccountComponent.Factory
    fun createArticlesComponent(): ArticlesComponent.Factory
    fun createTransactionsComponent(): TransactionsComponent.Factory
    fun createSettingsComponent(): SettingComponent.Factory

}