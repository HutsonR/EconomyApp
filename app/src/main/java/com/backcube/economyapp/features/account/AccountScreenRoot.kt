package com.backcube.economyapp.features.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.CustomFloatingButton
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.domain.utils.formatAsWholeThousands
import com.backcube.economyapp.features.account.store.models.AccountEffect
import com.backcube.economyapp.features.account.store.models.AccountIntent
import com.backcube.economyapp.features.account.store.models.AccountState
import com.backcube.economyapp.ui.theme.LightGreen
import com.backcube.economyapp.ui.theme.White
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
                onTrailingClick = {},
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

@Composable
fun AccountScreen(
    modifier: Modifier,
    navController: NavController,
    state: AccountState,
    effects: Flow<AccountEffect>,
    onIntent: (AccountIntent) -> Unit
) {
    var isAlertVisible by remember { mutableStateOf(false) }

    CollectEffect(effects) { effect ->
        when (effect) {
            AccountEffect.ShowClientError -> isAlertVisible = true
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
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        val item = state.item
        if (item != null) {
            Column {
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_balance),
                    leadingBackground = White,
                    leadingEmoji = "\uD83D\uDCB0",
                    isSmallItem = true,
                    trailingText = state.item.balance.formatAsWholeThousands(),
                    currencyIsoCode = state.item.currency,
                )
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_currency),
                    isSmallItem = true,
                    showLeading = false,
                    currencyIsoCode = state.item.currency,
                    showSeparator = false
                )
            }
        }
        CustomFloatingButton {
            // todo add expense
        }
    }
}