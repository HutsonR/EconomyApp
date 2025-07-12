package com.backcube.economyapp.features.articles.di

import com.backcube.economyapp.features.articles.ArticlesViewModel
import dagger.Subcomponent

@Subcomponent
interface ArticlesComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ArticlesComponent
    }

    val articlesViewModel: ArticlesViewModel
}