package com.backcube.transactions.presentation.analyze

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.backcube.domain.utils.formatAsWholeThousands
import com.backcube.navigation.AppNavigationController
import com.backcube.transactions.R
import com.backcube.transactions.common.di.TransactionsComponentProvider
import com.backcube.transactions.presentation.analyze.di.AnalyzeViewModelFactory
import com.backcube.transactions.presentation.analyze.models.AnalyzeEffect
import com.backcube.transactions.presentation.analyze.models.AnalyzeIntent
import com.backcube.transactions.presentation.analyze.models.AnalyzeState
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.AlertData
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.components.ShowAlertDialog
import com.backcube.ui.components.ShowProgressIndicator
import com.backcube.ui.components.date.CustomDatePicker
import com.backcube.ui.components.date.DateMode
import com.backcube.ui.utils.CollectEffect
import com.backcube.ui.utils.formatAsPeriodDate
import com.backcube.ui.utils.toCurrency
import kotlinx.coroutines.flow.Flow
import java.time.Instant

@Composable
fun AnalyzeScreenRoot(
    isIncome: Boolean,
    navController: AppNavigationController
) {
    val applicationContext = LocalContext.current.applicationContext
    val transactionComponent = (applicationContext as TransactionsComponentProvider).provideTransactionsComponent()
    val owner = LocalViewModelStoreOwner.current ?: error("No ViewModelStoreOwner found")

    val viewModel: AnalyzeViewModel = viewModel(
        viewModelStoreOwner = owner,
        factory = AnalyzeViewModelFactory(
            transactionComponent.analyzeViewModel,
            isIncome = isIncome
        )
    )

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.analyze_title),
                leadingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(AnalyzeIntent.GoBack) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        AnalyzeScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@Composable
internal fun AnalyzeScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: AnalyzeState,
    effects: Flow<AnalyzeEffect>,
    onIntent: (AnalyzeIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme

    var showDatePicker by remember { mutableStateOf<Pair<DateMode, Boolean>?>(null) }
    var isFetchAlertVisible by remember { mutableStateOf(false) }

    CollectEffect(effects) { effect ->
        when (effect) {
            AnalyzeEffect.GoBack -> navController.popBackStack()
            is AnalyzeEffect.ShowDatePickerModal -> showDatePicker = Pair(effect.dateMode, true)
            AnalyzeEffect.ShowFetchError -> isFetchAlertVisible = !isFetchAlertVisible
        }
    }

    if (isFetchAlertVisible) {
        ShowAlertDialog(
            alertData = AlertData(
                actionButtonTitle = com.backcube.ui.R.string.repeat
            ),
            onActionButtonClick = {
                onIntent(AnalyzeIntent.Refresh)
                isFetchAlertVisible = false
            }
        )
    }

    if (showDatePicker?.second == true) {
        CustomDatePicker(
            selectedDate = when (showDatePicker!!.first) {
                DateMode.START -> state.startAnalyzeDate.toEpochMilli()
                DateMode.END -> state.endAnalyzeDate.toEpochMilli()
            },
            mode = showDatePicker!!.first,
            onDateSelected = { mode, date ->
                onIntent(AnalyzeIntent.UpdateDate(mode, date))
                showDatePicker = null
            },
            onDismiss = { showDatePicker = null }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        if (state.isLoading) return

        CustomListItem(
            title = stringResource(R.string.analyze_period_start),
            isSmallItem = true,
            showLeading = false,
            trailingContent = {
                Date(
                    backgroundColor = colors.primary,
                    textColor = colors.onSurface,
                    date = state.startAnalyzeDate
                )
            },
            onItemClick = {
                onIntent(AnalyzeIntent.ShowCalendar(DateMode.START))
            }
        )
        CustomListItem(
            title = stringResource(R.string.analyze_period_end),
            isSmallItem = true,
            showLeading = false,
            trailingContent = {
                Date(
                    backgroundColor = colors.primary,
                    textColor = colors.onSurface,
                    date = state.endAnalyzeDate
                )
            },
            onItemClick = {
                onIntent(AnalyzeIntent.ShowCalendar(DateMode.END))
            }
        )

        if (state.items.isEmpty()) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(com.backcube.ui.R.string.empty),
                color = colors.onSurface,
                fontSize = 16.sp
            )
            return@Column
        }

        LazyColumn {
            item {
                CustomListItem(
                    title = stringResource(com.backcube.ui.R.string.amount),
                    isSmallItem = true,
                    showLeading = false,
                    showTrailingIcon = false,
                    trailingText = state.totalSum
                )
            }

            item {
                Column {
                    Spacer(modifier = Modifier.padding(vertical = 16.dp))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = colors.outlineVariant
                    )
                }
            }

            items(
                items = state.items,
                key = { it.name }
            ) { item ->
                val percent = "%.1f".format(item.percent) + "%"
                val sum = item.totalAmount.formatAsWholeThousands() + " " + state.currencyIsoCode.toCurrency()

                CustomListItem(
                    title = item.name,
                    leadingEmojiOrText = item.emoji,
                    showTrailingIcon = false,
                    trailingText = percent,
                    trailingSubText = sum
                )
            }
        }
    }
}

@Composable
internal fun Date(
    backgroundColor: Color,
    textColor: Color,
    date: Instant
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Text(
            text = date.formatAsPeriodDate(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}