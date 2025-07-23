package com.backcube.account.main

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.backcube.account.R
import com.backcube.account.common.di.AccountComponentProvider
import com.backcube.account.main.models.AccountEffect
import com.backcube.account.main.models.AccountIntent
import com.backcube.account.main.models.AccountState
import com.backcube.domain.utils.formatAsWholeThousands
import com.backcube.economyapp.features.account.common.components.SheetCurrencies
import com.backcube.navigation.AppNavigationController
import com.backcube.navigation.model.Screens
import com.backcube.ui.baseComponents.CustomTopBar
import com.backcube.ui.components.AlertData
import com.backcube.ui.components.CustomListItem
import com.backcube.ui.components.ShowAlertDialog
import com.backcube.ui.components.ShowProgressIndicator
import com.backcube.ui.components.graphics.AnimatedChartView
import com.backcube.ui.theme.LightGreen
import com.backcube.ui.theme.White
import com.backcube.ui.utils.CollectEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountScreenRoot(
    navController: AppNavigationController
) {
    val applicationContext = LocalContext.current.applicationContext
    val accountComponent = (applicationContext as AccountComponentProvider).provideAccountComponent()
    val viewModel = remember {
        accountComponent.accountViewModel
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.account_title),
                trailingIconPainter = painterResource(com.backcube.ui.R.drawable.ic_edit),
                onTrailingClick = { viewModel.handleIntent(AccountIntent.OnOpenEditScreen) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        AccountScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            state = state,
            effects = effects,
            onIntent = viewModel::handleIntent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AccountScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: AccountState,
    effects: Flow<AccountEffect>,
    onIntent: (AccountIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isIsoCodeSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isAlertVisible by remember { mutableStateOf(false) }
    var alertData by remember { mutableStateOf(AlertData()) }

    CollectEffect(effects) { effect ->
        when (effect) {
            is AccountEffect.ShowError -> {
                isAlertVisible = true
                alertData = effect.alertData
            }
            AccountEffect.ShowCurrencySheet -> isIsoCodeSheetOpen = !isIsoCodeSheetOpen
            is AccountEffect.OpenEditScreen -> navController.navigate(Screens.AccountEditScreen.createRoute(effect.accountId.toString()))
        }
    }

    if (isAlertVisible) {
        ShowAlertDialog(
            alertData = alertData,
            onActionButtonClick = {
                isAlertVisible = false
            }
        )
    }

    if (isIsoCodeSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = { isIsoCodeSheetOpen = !isIsoCodeSheetOpen }
        ) {
            SheetCurrencies(
                currencies = state.currencies,
                onCurrencyClick = {
                    onIntent(AccountIntent.OnCurrencySelected(it))
                    isIsoCodeSheetOpen = !isIsoCodeSheetOpen
                },
                onCancel = { isIsoCodeSheetOpen = !isIsoCodeSheetOpen }
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        if (state.isLoading) return@Column
        val item = state.item
        if (item != null) {
            Column {
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_name),
                    isSmallItem = true,
                    trailingText = state.item.name,
                    showLeading = false,
                    showTrailingIcon = false
                )
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_balance),
                    leadingBackground = White,
                    leadingEmojiOrText = "\uD83D\uDCB0",
                    isSmallItem = true,
                    trailingText = state.item.balance.formatAsWholeThousands(showDecimals = true),
                    currencyIsoCode = state.item.currency,
                )
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_currency),
                    isSmallItem = true,
                    showLeading = false,
                    currencyIsoCode = state.item.currency,
                    showSeparator = false,
                    onItemClick = { onIntent(AccountIntent.OnOpenIsoCodeSheet) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        AnimatedChartView(
            points = state.chartPoints,
            chartType = state.chartType,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        )

        ChangeButton(
            modifier = Modifier.padding(20.dp),
            text = R.string.account_change_graphics_button,
            buttonBackground = MaterialTheme.colorScheme.primary,
            buttonTextColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { onIntent(AccountIntent.OnChangeGraphType) }
        )
    }
}

@Composable
fun ChangeButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    buttonBackground: Color,
    buttonTextColor: Color,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackground
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(text),
            color = buttonTextColor
        )
    }
}
