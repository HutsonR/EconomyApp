package com.backcube.economyapp.features.transactions.presentation.histories

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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.di.appComponent
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.components.date.CustomDatePicker
import com.backcube.economyapp.core.ui.components.date.DateMode
import com.backcube.economyapp.core.ui.theme.LightGreen
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.core.ui.utils.formatAsPeriodDate
import com.backcube.economyapp.core.ui.utils.formatAsTransactionDate
import com.backcube.economyapp.domain.utils.CurrencyIsoCode
import com.backcube.economyapp.domain.utils.formatAsWholeThousands
import com.backcube.economyapp.features.transactions.presentation.histories.store.models.HistoryEffect
import com.backcube.economyapp.features.transactions.presentation.histories.store.models.HistoryIntent
import com.backcube.economyapp.features.transactions.presentation.histories.store.models.HistoryState
import kotlinx.coroutines.flow.Flow

@Composable
fun HistoryScreenRoot(
    isIncome: Boolean,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel = remember {
        context.appComponent
            .createTransactionsComponent()
            .create()
            .historyViewModel
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    LaunchedEffect(isIncome) {
        viewModel.setIncomeFlag(isIncome)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.history_title),
                leadingIconPainter = painterResource(R.drawable.ic_back),
                onLeadingClick = { viewModel.handleIntent(HistoryIntent.GoBack) },
                trailingIconPainter = painterResource(R.drawable.ic_clipboard),
                onTrailingClick = {},
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        HistoryScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
fun HistoryScreen(
    modifier: Modifier,
    navController: NavController,
    state: HistoryState,
    effects: Flow<HistoryEffect>,
    onIntent: (HistoryIntent) -> Unit
) {
    var isAlertVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf<Pair<DateMode, Boolean>?>(null) }

    CollectEffect(effects) { effect ->
        when (effect) {
            HistoryEffect.GoBack -> navController.popBackStack()
            is HistoryEffect.ShowCalendar -> showDatePicker = Pair(effect.dateMode, true)
            HistoryEffect.ShowClientError -> isAlertVisible = true
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
            modifier = Modifier.background(LightGreen),
            title = stringResource(R.string.start),
            isSmallItem = true,
            showLeading = false,
            showTrailingIcon = false,
            trailingText = state.startHistoryDate.formatAsPeriodDate(),
            onItemClick = {
                onIntent(HistoryIntent.ShowCalendar(DateMode.START))
            }
        )
        CustomListItem(
            modifier = Modifier.background(LightGreen),
            title = stringResource(R.string.end),
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
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.amount),
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
                    trailingSubText = item.transactionDate.formatAsTransactionDate()
                )
            }
        }
    }
}