package com.backcube.economyapp.features.articles.store.models

sealed interface ArticleIntent {
    data class OnChangeQuery(val text: String) : ArticleIntent
}