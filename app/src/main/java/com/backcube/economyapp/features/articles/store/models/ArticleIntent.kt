package com.backcube.economyapp.features.articles.store.models

sealed interface ArticleIntent {
    data class OnSearchQuery(val text: String) : ArticleIntent
}