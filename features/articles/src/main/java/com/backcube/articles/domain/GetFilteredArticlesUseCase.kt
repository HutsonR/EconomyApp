package com.backcube.articles.domain

import com.backcube.domain.models.categories.CategoryModel
import com.backcube.domain.utils.qualifiers.DefaultDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFilteredArticlesUseCase @Inject constructor(
    @DefaultDispatchers private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(list: List<CategoryModel>, query: String): List<CategoryModel> =
        withContext(dispatcher) {
            list.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
}