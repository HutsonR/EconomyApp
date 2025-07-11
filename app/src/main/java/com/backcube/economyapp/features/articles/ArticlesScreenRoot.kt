package com.backcube.economyapp.features.articles

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.di.appComponent
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.CustomTextInput
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.features.articles.store.models.ArticleEffect
import com.backcube.economyapp.features.articles.store.models.ArticleIntent
import com.backcube.economyapp.features.articles.store.models.ArticleState
import kotlinx.coroutines.flow.Flow

@Composable
fun ArticlesScreenRoot(
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel = remember {
        context.appComponent
            .createArticlesComponent()
            .create()
            .articlesViewModel
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.article_title),
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
fun ArticlesScreen(
    modifier: Modifier,
    navController: NavController,
    state: ArticleState,
    effects: Flow<ArticleEffect>,
    onIntent: (ArticleIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme
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
            placeholder = stringResource(id = R.string.article_search),
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