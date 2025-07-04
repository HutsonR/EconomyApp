package com.backcube.economyapp.features.articles.store.models

import com.backcube.economyapp.domain.models.categories.CategoryModel

data class ArticleState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val searchQuery: String = "",
    val initialItems: List<CategoryModel> = emptyList(),
    val filteredItems: List<CategoryModel> = emptyList()
)
