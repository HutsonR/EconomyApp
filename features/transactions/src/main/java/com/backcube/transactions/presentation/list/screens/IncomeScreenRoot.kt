package com.backcube.transactions.presentation.list.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.domain.utils.formatAsWholeThousands
import com.backcube.navigation.AppNavigationController
import com.backcube.navigation.model.Screens
import com.backcube.transactions.R
import com.backcube.transactions.common.di.TransactionsComponentProvider
import com.backcube.transactions.presentation.list.di.TransactionsViewModelFactory
import com.backcube.transactions.presentation.list.models.TransactionEffect
import com.backcube.transactions.presentation.list.models.TransactionIntent
import com.backcube.transactions.presentation.list.models.TransactionState
import com.backcube.transactions.presentation.list.viewmodels.TransactionsViewModel
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.AlertData
import com.backcube.ui.components.CustomFloatingButton
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.components.ShowAlertDialog
import com.backcube.ui.components.ShowProgressIndicator
import com.backcube.ui.theme.LightGreen
import com.backcube.ui.utils.CollectEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun IncomesScreenRoot(
    navController: AppNavigationController
) {
    val context = LocalContext.current
    val applicationContext = LocalContext.current.applicationContext
    val transactionComponent = (applicationContext as TransactionsComponentProvider).provideTransactionsComponent()

    val viewModel: TransactionsViewModel = remember {
        val factory = transactionComponent.transactionsViewModel

        ViewModelProvider(
            context as ViewModelStoreOwner,
            TransactionsViewModelFactory(factory, isIncome = true)
        )["incomes", TransactionsViewModel::class.java]
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.income_title),
                trailingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_history),
                onTrailingClick = {
                    viewModel.handleIntent(TransactionIntent.GoToHistory)
                },
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
internal fun IncomeScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: TransactionState,
    effects: Flow<TransactionEffect>,
    onIntent: (TransactionIntent) -> Unit
) {
    var isAlertVisible by remember { mutableStateOf(false) }

    CollectEffect(effects) { effect ->
        when (effect) {
            TransactionEffect.NavigateToHistory -> navController.navigate(Screens.HistoryScreen.createRoute(true))
            TransactionEffect.ShowClientError -> isAlertVisible = true
            is TransactionEffect.NavigateToEditorTransaction -> {
                navController.navigate(Screens.TransactionEditScreen.createRoute(effect.transactionId, true))
            }
        }
    }

    if (isAlertVisible) {
        ShowAlertDialog(
            alertData = AlertData(
                actionButtonTitle = com.backcube.ui.R.string.repeat
            ),
            onActionButtonClick = {
                onIntent(TransactionIntent.Refresh)
                isAlertVisible = false
            }
        )
    }

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
                    title = stringResource(com.backcube.ui.R.string.total),
                    isSmallItem = true,
                    showLeading = false,
                    showTrailingIcon = false,
                    trailingText = state.totalSum.formatAsWholeThousands(),
                    currencyIsoCode = state.items.firstOrNull()?.account?.currency ?: CurrencyIsoCode.RUB
                )
            }

            items(
                items = state.items,
                key = { it.id }
            ) { item ->
                CustomListItem(
                    title = item.category.name,
                    subtitle = item.comment,
                    leadingEmojiOrText = item.category.emoji,
                    trailingText = item.amount.formatAsWholeThousands(),
                    currencyIsoCode = item.account.currency,
                    onItemClick = {
                        onIntent(TransactionIntent.EditTransaction(item.id))
                    }
                )
            }
        }
        CustomFloatingButton {
            onIntent(TransactionIntent.AddTransaction)
        }
    }
}