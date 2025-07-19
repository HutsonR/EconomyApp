package com.backcube.economyapp

import android.content.Context
import com.backcube.account.common.di.AccountComponent
import com.backcube.articles.di.ArticlesComponent
import com.backcube.data.common.di.DataModule
import com.backcube.domain.di.DomainModule
import com.backcube.economyapp.main.MainActivity
import com.backcube.settings.di.SettingComponent
import com.backcube.transactions.common.di.TransactionsComponent
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