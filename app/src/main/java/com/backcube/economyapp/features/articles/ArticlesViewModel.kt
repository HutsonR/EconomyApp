package com.backcube.economyapp.features.articles

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.repositories.CategoryRepository
import com.backcube.economyapp.features.articles.store.models.ArticleEffect
import com.backcube.economyapp.features.articles.store.models.ArticleIntent
import com.backcube.economyapp.features.articles.store.models.ArticleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel<ArticleState, ArticleEffect>(ArticleState()) {

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                modifyState { copy(isLoading = true) }
                val result = categoryRepository.getCategories()

                modifyState {
                    copy(
                        items = result
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                modifyState { copy(isLoading = false) }
            }
        }
    }

    fun handleIntent(intent: ArticleIntent) {
        // todo Дальше больше
        when(intent) {
            is ArticleIntent.OnSearchQuery -> modifyState { copy(searchQuery = intent.text) }
        }
    }
}