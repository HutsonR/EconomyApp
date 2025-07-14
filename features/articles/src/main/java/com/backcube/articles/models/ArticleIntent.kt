package com.backcube.articles.models

internal sealed interface ArticleIntent {
    data class OnChangeQuery(val text: String) : ArticleIntent
}