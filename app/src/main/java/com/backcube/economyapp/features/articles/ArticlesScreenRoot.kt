package com.backcube.economyapp.features.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.features.articles.store.models.ArticleEffect
import com.backcube.economyapp.features.articles.store.models.ArticleIntent
import com.backcube.economyapp.features.articles.store.models.ArticleState
import com.backcube.economyapp.features.common.baseComponents.CustomTopBar
import com.backcube.economyapp.features.common.ui.CustomListItem
import com.backcube.economyapp.features.common.ui.CustomTextInput
import com.backcube.economyapp.features.common.ui.ShowProgressIndicator
import kotlinx.coroutines.flow.Flow

@Composable
fun ArticlesScreenRoot(
    navController: NavController,
    viewModel: ArticlesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
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
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        CustomTextInput(
            value = query,
            onValueChange = { query = it },
            placeholder = stringResource(id = R.string.article_search),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = colors.surfaceContainerHigh,
                unfocusedContainerColor = colors.surfaceContainerHigh,
                disabledContainerColor = colors.surfaceContainerHigh,
                unfocusedBorderColor = colors.outlineVariant
            )
        )
        LazyColumn {
            items(
                items = state.items,
                key = { it.id }
            ) { item ->
                CustomListItem(
                    title = item.name,
                    leadingEmoji = item.emoji,
                    showTrailingIcon = false
                )
            }
        }
    }
}