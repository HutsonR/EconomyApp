package com.backcube.economyapp.features.account.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.navigation.Screens
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.CustomFloatingButton
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.theme.LightGreen
import com.backcube.economyapp.core.ui.theme.White
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.domain.utils.formatAsWholeThousands
import com.backcube.economyapp.features.account.common.components.SheetCurrencies
import com.backcube.economyapp.features.account.main.store.models.AccountEffect
import com.backcube.economyapp.features.account.main.store.models.AccountIntent
import com.backcube.economyapp.features.account.main.store.models.AccountState
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountScreenRoot(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.account_title),
                trailingIconPainter = painterResource(R.drawable.ic_edit),
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
fun AccountScreen(
    modifier: Modifier,
    navController: NavController,
    state: AccountState,
    effects: Flow<AccountEffect>,
    onIntent: (AccountIntent) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isIsoCodeSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isAlertVisible by remember { mutableStateOf(false) }

    CollectEffect(effects) { effect ->
        when (effect) {
            AccountEffect.ShowClientError -> isAlertVisible = true
            AccountEffect.ShowCurrencySheet -> isIsoCodeSheetOpen = !isIsoCodeSheetOpen
            is AccountEffect.OpenEditScreen -> navController.navigate(Screens.AccountEditScreen.createRoute(effect.accountId.toString()))
        }
    }

    if (isAlertVisible) {
        ShowAlertDialog(
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
        CustomFloatingButton {
            // todo add expense
        }
    }
}