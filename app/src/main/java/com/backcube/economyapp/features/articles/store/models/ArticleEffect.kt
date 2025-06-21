package com.backcube.economyapp.features.articles.store.models

sealed interface ArticleEffect {
    data object ShowClientError : ArticleEffect
}