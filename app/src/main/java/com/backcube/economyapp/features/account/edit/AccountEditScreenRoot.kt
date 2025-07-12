package com.backcube.economyapp.features.account.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.di.appComponent
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.CustomTextInput
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.features.account.common.components.SheetCurrencies
import com.backcube.economyapp.features.account.edit.store.models.AccountEditEffect
import com.backcube.economyapp.features.account.edit.store.models.AccountEditIntent
import com.backcube.economyapp.features.account.edit.store.models.AccountEditState
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountEditScreenRoot(
    accountId: Int,
    navController: NavController
) {
    val context = LocalContext.current
    val viewModel = remember {
        context.appComponent
            .createAccountComponent()
            .create()
            .accountEditViewModel
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    LaunchedEffect(accountId) {
        viewModel.fetchData(accountId)
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.account_title),
                leadingIconPainter = painterResource(R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(AccountEditIntent.OnCancelClick) },
                trailingIconPainter = painterResource(R.drawable.ic_check),
                onTrailingClick = { viewModel.handleIntent(AccountEditIntent.OnSaveClick) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        AccountEditScreen(
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
fun AccountEditScreen(
    modifier: Modifier,
    navController: NavController,
    state: AccountEditState,
    effects: Flow<AccountEditEffect>,
    onIntent: (AccountEditIntent) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val defaultTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colors.onSurface,
        unfocusedTextColor = colors.onSurface,
        focusedContainerColor = colors.surface,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent
    )
    val defaultMainTextStyle = TextStyle(
        color = colors.onSurface,
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.End
    )
    val defaultLeadingTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        color = colors.onSurface
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isIsoCodeSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isAlertVisible by remember { mutableStateOf(false) }

    val queryAccountName = state.item?.name ?: ""
    val queryBalanceRaw = state.balance

    CollectEffect(effects) { effect ->
        when (effect) {
            AccountEditEffect.ShowClientError -> isAlertVisible = true
            AccountEditEffect.ShowCurrencySheet -> isIsoCodeSheetOpen = !isIsoCodeSheetOpen
            AccountEditEffect.GoBack -> navController.popBackStack()
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
                    onIntent(AccountEditIntent.OnCurrencySelected(it))
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
                CustomTextInput(
                    value = queryAccountName,
                    onValueChange = {
                        onIntent(AccountEditIntent.OnAccountNameChange(it))
                    },
                    leadingText = stringResource(id = R.string.account_name),
                    textStyle = defaultMainTextStyle,
                    leadingTextStyles = defaultLeadingTextStyle,
                    colors = defaultTextFieldColors
                )
                CustomTextInput(
                    value = queryBalanceRaw,
                    onValueChange = {
                        if (validateBalanceInput(it)) {
                            onIntent(AccountEditIntent.OnAccountBalanceChange(it))
                        }
                    },
                    textStyle = defaultMainTextStyle,
                    leadingTextStyles = defaultLeadingTextStyle,
                    leadingText = stringResource(id = R.string.account_balance),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    colors = defaultTextFieldColors
                )
                CustomListItem(
                    title = stringResource(R.string.account_currency),
                    isSmallItem = true,
                    showLeading = false,
                    currencyIsoCode = state.item.currency,
                    onItemClick = { onIntent(AccountEditIntent.OnOpenIsoCodeSheet) }
                )
            }
        }
    }
}

private fun validateBalanceInput(input: String): Boolean {
    if (input.isEmpty()) return true
    val regex = Regex("""^-?\d+([.,]\d{0,2})?$""")
    return regex.matches(input)
}