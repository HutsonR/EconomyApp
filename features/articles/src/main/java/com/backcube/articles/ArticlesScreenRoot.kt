package com.backcube.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.articles.di.ArticlesComponentProvider
import com.backcube.articles.models.ArticleEffect
import com.backcube.articles.models.ArticleIntent
import com.backcube.articles.models.ArticleState
import com.backcube.navigation.AppNavigationController
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.components.CustomTextInput
import com.backcube.ui.components.ShowAlertDialog
import com.backcube.ui.components.ShowProgressIndicator
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import kotlinx.coroutines.flow.Flow

@Composable
fun ArticlesScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val articleComponent = (applicationContext as ArticlesComponentProvider).provideArticlesComponent()
    val viewModel = remember {
        articleComponent.articlesViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.article_title),
                onTrailingClick = {},
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        ArticlesScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun ArticlesScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: ArticleState,
    effects: Flow<ArticleEffect>,
    onIntent: (ArticleIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val context = LocalAppContext.current
    var query by remember { mutableStateOf("") }
    var isAlertVisible by remember { mutableStateOf(false) }

    CollectEffect(effects) { effect ->
        when (effect) {
            ArticleEffect.ShowClientError -> isAlertVisible = true
        }
    }

    if (isAlertVisible) {
        ShowAlertDialog(
            onActionButtonClick = {
                isAlertVisible = false
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        CustomTextInput(
            value = query,
            onValueChange = {
                query = it
                onIntent(ArticleIntent.OnChangeQuery(it))
            },
            placeholder = context.getString(R.string.article_search),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.onSurface,
                unfocusedTextColor = colors.onSurface,
                focusedContainerColor = colors.surfaceContainerHigh,
                unfocusedContainerColor = colors.surfaceContainerHigh,
                disabledContainerColor = colors.surfaceContainerHigh,
                unfocusedBorderColor = Color.Transparent
            )
        )
        LazyColumn {
            items(
                items = state.filteredItems,
                key = { it.id }
            ) { item ->
                CustomListItem(
                    title = item.name,
                    leadingEmojiOrText = item.emoji,
                    showTrailingIcon = false
                )
            }
        }
    }
}