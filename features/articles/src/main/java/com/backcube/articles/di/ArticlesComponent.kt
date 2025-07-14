package com.backcube.articles.di

import com.backcube.articles.ArticlesViewModel
import dagger.Subcomponent

@Subcomponent
interface ArticlesComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): ArticlesComponent
    }

    val articlesViewModel: ArticlesViewModel
}