package com.backcube.economyapp

import android.content.Context
import com.backcube.economyapp.data.di.DataModule
import com.backcube.economyapp.domain.di.DomainModule
import com.backcube.economyapp.features.account.common.di.AccountComponent
import com.backcube.economyapp.features.articles.di.ArticlesComponent
import com.backcube.economyapp.features.expenses.di.ExpenseComponent
import com.backcube.economyapp.features.histories.di.HistoryComponent
import com.backcube.economyapp.features.income.di.IncomeComponent
import com.backcube.economyapp.features.settings.di.SettingComponent
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
    fun createExpenseComponent(): ExpenseComponent.Factory
    fun createHistoryComponent(): HistoryComponent.Factory
    fun createIncomeComponent(): IncomeComponent.Factory
    fun createSettingsComponent(): SettingComponent.Factory

}