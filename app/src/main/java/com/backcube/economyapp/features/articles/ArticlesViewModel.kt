package com.backcube.economyapp.features.articles

import androidx.lifecycle.viewModelScope
import com.backcube.economyapp.core.BaseViewModel
import com.backcube.economyapp.domain.usecases.api.CategoryUseCase
import com.backcube.economyapp.features.articles.domain.GetFilteredArticlesUseCase
import com.backcube.economyapp.features.articles.store.models.ArticleEffect
import com.backcube.economyapp.features.articles.store.models.ArticleIntent
import com.backcube.economyapp.features.articles.store.models.ArticleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val getFilteredArticlesUseCase: GetFilteredArticlesUseCase
) : BaseViewModel<ArticleState, ArticleEffect>(ArticleState()) {

    private var changeQuerySearchJob: Job? = null

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            modifyState { copy(isLoading = true) }

            categoryUseCase.getCategories().fold(
                onSuccess = {
                    modifyState {
                        copy(
                            initialItems = it,
                            filteredItems = it
                        )
                    }
                },
                onFailure = {
                    it.printStackTrace()
                    effect(ArticleEffect.ShowClientError)
                }
            )

            modifyState { copy(isLoading = false) }
        }
    }

    fun handleIntent(intent: ArticleIntent) {
        when(intent) {
            is ArticleIntent.OnChangeQuery -> onChangeQueryAction(intent.text)
        }
    }

    private fun onChangeQueryAction(query: String) {
        modifyState { copy(searchQuery = query) }
        changeQuerySearchJob?.cancel()
        changeQuerySearchJob = viewModelScope.launch {
            delay(DELAY_QUERY_SEARCH)
            val initialItems = getState().initialItems
            val filteredItems = if (query.isBlank()) {
                initialItems
            } else {
                getFilteredArticlesUseCase(initialItems, query)
            }
            modifyState { copy(filteredItems = filteredItems) }
        }
    }

    companion object {
        private const val DELAY_QUERY_SEARCH = 300L
    }
}