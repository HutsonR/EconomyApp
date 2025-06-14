package com.backcube.economyapp.features.income

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.domain.utils.formatAsWholeThousands
import com.backcube.economyapp.features.common.baseComponents.CustomTopBar
import com.backcube.economyapp.features.common.ui.CustomFloatingButton
import com.backcube.economyapp.features.common.ui.CustomListItem
import com.backcube.economyapp.features.common.ui.ShowProgressIndicator
import com.backcube.economyapp.features.income.store.models.IncomeEffect
import com.backcube.economyapp.features.income.store.models.IncomeIntent
import com.backcube.economyapp.features.income.store.models.IncomeState
import com.backcube.economyapp.ui.theme.LightGreen
import kotlinx.coroutines.flow.Flow

@Composable
fun IncomesScreenRoot(
    navController: NavController,
    viewModel: IncomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.income_title),
                trailingIconPainter = painterResource(R.drawable.ic_history),
                onTrailingClick = {},
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        IncomeScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
fun IncomeScreen(
    modifier: Modifier,
    navController: NavController,
    state: IncomeState,
    effects: Flow<IncomeEffect>,
    onIntent: (IncomeIntent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        LazyColumn {
            item {
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.total),
                    isSmallItem = true,
                    showLeading = false,
                    showTrailingIcon = false,
                    trailingText = state.totalSum.formatAsWholeThousands()
                )
            }

            items(
                items = state.items,
                key = { it.id }
            ) { item ->
                CustomListItem(
                    title = item.category.name,
                    showLeading = false,
                    trailingText = item.amount.formatAsWholeThousands()
                )
            }
        }
        CustomFloatingButton {
            // todo add expense
        }
    }
}