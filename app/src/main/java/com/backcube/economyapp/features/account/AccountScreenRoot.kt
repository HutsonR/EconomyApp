package com.backcube.economyapp.features.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.backcube.economyapp.domain.utils.toCurrency
import com.backcube.economyapp.features.account.store.models.AccountEffect
import com.backcube.economyapp.features.account.store.models.AccountIntent
import com.backcube.economyapp.features.account.store.models.AccountState
import com.backcube.economyapp.features.common.baseComponents.CustomTopBar
import com.backcube.economyapp.features.common.ui.CustomFloatingButton
import com.backcube.economyapp.features.common.ui.CustomListItem
import com.backcube.economyapp.features.common.ui.ShowProgressIndicator
import com.backcube.economyapp.ui.theme.LightGreen
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountScreenRoot(
    navController: NavController,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
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
                    leadingEmoji = "\uD83D\uDCB0",
                    isSmallItem = true,
                    trailingText = state.item.balance.formatAsWholeThousands()
                )
                CustomListItem(
                    modifier = Modifier.background(LightGreen),
                    title = stringResource(R.string.account_currency),
                    isSmallItem = true,
                    showLeading = false,
                    trailingText = state.item.currency.toCurrency(),
                    showCurrency = false,
                    showSeparator = false
                )
            }
        }
        CustomFloatingButton {
            // todo add expense
        }
    }
}