package com.backcube.transactions.presentation.histories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.domain.utils.formatAsWholeThousands
import com.backcube.navigation.AppNavigationController
import com.backcube.navigation.model.Screens
import com.backcube.transactions.R
import com.backcube.transactions.common.di.TransactionsComponentProvider
import com.backcube.transactions.presentation.histories.models.HistoryEffect
import com.backcube.transactions.presentation.histories.models.HistoryIntent
import com.backcube.transactions.presentation.histories.models.HistoryState
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.components.ShowAlertDialog
import com.backcube.ui.components.ShowProgressIndicator
import com.backcube.ui.components.date.CustomDatePicker
import com.backcube.ui.components.date.DateMode
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.LocalAppContext
import com.backcube.ui.utils.formatAsPeriodDate
import com.backcube.ui.utils.formatAsTransactionDate
import kotlinx.coroutines.flow.Flow

@Composable
fun HistoryScreenRoot(
    isIncome: Boolean,
    navController: AppNavigationController
) {
    val context = LocalAppContext.current
    val applicationContext = LocalContext.current.applicationContext
    val transactionComponent = (applicationContext as TransactionsComponentProvider).provideTransactionsComponent()
    val viewModel = remember {
        transactionComponent.historyViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    LaunchedEffect(isIncome) {
        viewModel.setIncomeFlag(isIncome)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = context.getString(R.string.history_title),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_back),
                onLeadingClick = { viewModel.handleIntent(HistoryIntent.GoBack) },
                trailingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_clipboard),
                onTrailingClick = { viewModel.handleIntent(HistoryIntent.GoAnalyze(isIncome)) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        HistoryScreen(
            modifier = Modifier.padding(innerPadding),
            isIncome = isIncome,
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun HistoryScreen(
    modifier: Modifier,
    isIncome: Boolean,
    navController: AppNavigationController,
    state: HistoryState,
    effects: Flow<HistoryEffect>,
    onIntent: (HistoryIntent) -> Unit
) {
    val context = LocalAppContext.current
    val colors = MaterialTheme.colorScheme
    var isAlertVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf<Pair<DateMode, Boolean>?>(null) }

    CollectEffect(effects) { effect ->
        when (effect) {
            HistoryEffect.GoBack -> navController.popBackStack()
            is HistoryEffect.ShowCalendar -> showDatePicker = Pair(effect.dateMode, true)
            HistoryEffect.ShowClientError -> isAlertVisible = true
            is HistoryEffect.NavigateToEditorTransaction -> {
                navController.navigate(
                    Screens.TransactionEditScreen.createRoute(effect.transactionId, isIncome = isIncome)
                )
            }
            is HistoryEffect.NavigateToAnalyze -> {
                navController.navigate(
                    Screens.AnalyzeScreen.createRoute(isIncome = effect.isIncome)
                )
            }
        }
    }

    if (isAlertVisible) {
        ShowAlertDialog(
            onActionButtonClick = {
                isAlertVisible = false
            }
        )
    }

    if (showDatePicker?.second == true) {
        CustomDatePicker(
            selectedDate = when (showDatePicker!!.first) {
                DateMode.START -> state.startHistoryDate.toEpochMilli()
                DateMode.END -> state.endHistoryDate.toEpochMilli()
            },
            mode = showDatePicker!!.first,
            onDateSelected = { mode, date ->
                onIntent(HistoryIntent.UpdateDate(mode, date))
                showDatePicker = null
            },
            onDismiss = { showDatePicker = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        CustomListItem(
            modifier = Modifier.background(colors.surfaceVariant),
            title = context.getString(com.backcube.ui.R.string.start),
            isSmallItem = true,
            showLeading = false,
            showTrailingIcon = false,
            trailingText = state.startHistoryDate.formatAsPeriodDate(),
            onItemClick = {
                onIntent(HistoryIntent.ShowCalendar(DateMode.START))
            }
        )
        CustomListItem(
            modifier = Modifier.background(colors.surfaceVariant),
            title = context.getString(com.backcube.ui.R.string.end),
            isSmallItem = true,
            showLeading = false,
            showTrailingIcon = false,
            trailingText = state.endHistoryDate.formatAsPeriodDate(),
            onItemClick = {
                onIntent(HistoryIntent.ShowCalendar(DateMode.END))
            }
        )
        ShowProgressIndicator(state.isLoading)
        LazyColumn {
            item {
                CustomListItem(
                    modifier = Modifier.background(colors.surfaceVariant),
                    title = context.getString(com.backcube.ui.R.string.amount),
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
                    trailingSubText = item.transactionDate.formatAsTransactionDate(),
                    onItemClick = {
                        onIntent(HistoryIntent.EditTransaction(item.id))
                    }
                )
            }
        }
    }
}