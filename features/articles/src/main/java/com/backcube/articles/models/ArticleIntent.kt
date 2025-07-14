package com.backcube.articles.models

sealed interface ArticleIntent {
    data class OnChangeQuery(val text: String) : ArticleIntent
}