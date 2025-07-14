package com.backcube.articles.models

sealed interface ArticleEffect {
    data object ShowClientError : ArticleEffect
}