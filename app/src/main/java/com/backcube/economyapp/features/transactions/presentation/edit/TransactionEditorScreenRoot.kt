package com.backcube.economyapp.features.transactions.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.backcube.economyapp.R
import com.backcube.economyapp.core.di.appComponent
import com.backcube.economyapp.core.navigation.AppNavigationController
import com.backcube.economyapp.core.ui.baseComponents.CustomTopBar
import com.backcube.economyapp.core.ui.components.AlertData
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.components.CustomTextInput
import com.backcube.economyapp.core.ui.components.CustomTimePicker
import com.backcube.economyapp.core.ui.components.ShowAlertDialog
import com.backcube.economyapp.core.ui.components.ShowProgressIndicator
import com.backcube.economyapp.core.ui.components.date.CustomDatePicker
import com.backcube.economyapp.core.ui.components.date.DateMode
import com.backcube.economyapp.core.ui.utils.CollectEffect
import com.backcube.economyapp.core.ui.utils.formatAsSimpleDate
import com.backcube.economyapp.core.ui.utils.formatAsSimpleTime
import com.backcube.economyapp.core.ui.utils.toCurrency
import com.backcube.economyapp.core.ui.utils.toHourMinute
import com.backcube.economyapp.features.transactions.presentation.common.components.SheetAccounts
import com.backcube.economyapp.features.transactions.presentation.common.components.SheetCategories
import com.backcube.economyapp.features.transactions.presentation.edit.di.TransactionEditorViewModelFactory
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorEffect
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorIntent
import com.backcube.economyapp.features.transactions.presentation.edit.store.models.TransactionEditorState
import kotlinx.coroutines.flow.Flow

@Composable
fun TransactionEditorScreenRoot(
    transactionId: Int,
    isIncome: Boolean,
    navController: AppNavigationController
) {
    val context = LocalContext.current
    val owner = LocalViewModelStoreOwner.current ?: error("No ViewModelStoreOwner found")

    val viewModel: TransactionEditorViewModel = viewModel(
        viewModelStoreOwner = owner,
        factory = TransactionEditorViewModelFactory(
            context.appComponent
                .createTransactionsComponent()
                .create()
                .transactionEditorViewModel,
            transactionId = transactionId
        )
    )
    val state by viewModel.state.collectAsStateWithLifecycle()
    val effects = viewModel.effect

    val topBarTitle = if (isIncome) {
        stringResource(R.string.my_incomes_title)
    } else {
        stringResource(R.string.my_expenses_title)
    }
    Scaffold(
        topBar = {
            CustomTopBar(
                title = topBarTitle,
                leadingIconPainter = painterResource(R.drawable.ic_close),
                onLeadingClick = { viewModel.handleIntent(TransactionEditorIntent.OnCancelClick) },
                trailingIconPainter = painterResource(R.drawable.ic_check),
                onTrailingClick = { viewModel.handleIntent(TransactionEditorIntent.OnSaveClick) },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        }
    ) { innerPadding ->
        TransactionEditorScreen(
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
fun TransactionEditorScreen(
    modifier: Modifier,
    navController: AppNavigationController,
    state: TransactionEditorState,
    effects: Flow<TransactionEditorEffect>,
    onIntent: (TransactionEditorIntent) -> Unit
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

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isAccountSheetOpen by rememberSaveable { mutableStateOf(false) }
    var isCategorySheetOpen by rememberSaveable { mutableStateOf(false) }
    var isDatePickerOpen by rememberSaveable { mutableStateOf(false) }
    var isTimePickerOpen by rememberSaveable { mutableStateOf(false) }
    var isFetchAlertVisible by remember { mutableStateOf(false) }
    var isClientAlertVisible by remember { mutableStateOf(false) }
    var clientAlertData by remember { mutableStateOf(AlertData()) }

    val queryTransactionDescription = state.comment
    val queryAmountRaw = state.amount

    CollectEffect(effects) { effect ->
        when (effect) {
            TransactionEditorEffect.GoBack -> navController.popBackStack()
            TransactionEditorEffect.ShowAccountSheet -> isAccountSheetOpen = !isAccountSheetOpen
            TransactionEditorEffect.ShowCategorySheet -> isCategorySheetOpen = !isCategorySheetOpen
            TransactionEditorEffect.ShowDatePickerModal -> isDatePickerOpen = !isDatePickerOpen
            TransactionEditorEffect.ShowTimePickerModal -> isTimePickerOpen = !isTimePickerOpen
            TransactionEditorEffect.ShowFetchError -> isFetchAlertVisible = !isFetchAlertVisible
            is TransactionEditorEffect.ShowClientError -> {
                clientAlertData = effect.alertData
                isClientAlertVisible = !isClientAlertVisible
            }
        }
    }

    if (isFetchAlertVisible) {
        ShowAlertDialog(
            alertData = AlertData(
                actionButtonTitle = R.string.repeat
            ),
            onActionButtonClick = {
                onIntent(TransactionEditorIntent.Refresh)
                isFetchAlertVisible = false
            }
        )
    }

    if (isClientAlertVisible) {
        ShowAlertDialog(
            alertData = clientAlertData,
            onActionButtonClick = {
                isClientAlertVisible = false
            }
        )
    }

    if (isAccountSheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = { isAccountSheetOpen = !isAccountSheetOpen }
        ) {
            SheetAccounts(
                currencies = state.accounts,
                onCurrencyClick = {
                    onIntent(TransactionEditorIntent.OnAccountSelected(it))
                    isAccountSheetOpen = !isAccountSheetOpen
                },
                onCancel = { isAccountSheetOpen = !isAccountSheetOpen }
            )
        }
    }

    if (isCategorySheetOpen) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = { isCategorySheetOpen = !isCategorySheetOpen }
        ) {
            SheetCategories(
                currencies = state.categories,
                onCurrencyClick = {
                    onIntent(TransactionEditorIntent.OnCategorySelected(it))
                    isCategorySheetOpen = !isCategorySheetOpen
                },
                onCancel = { isCategorySheetOpen = !isCategorySheetOpen }
            )
        }
    }

    if (isDatePickerOpen) {
        CustomDatePicker(
            selectedDate = state.selectedTransactionDate.toEpochMilli(),
            mode = DateMode.START,
            onDateSelected = { _, date ->
                onIntent(TransactionEditorIntent.OnDateSelected(date))
                isDatePickerOpen = !isDatePickerOpen
            },
            onDismiss = { isDatePickerOpen = !isDatePickerOpen }
        )
    }

    if (isTimePickerOpen) {
        val (initHour, initMinute) = state.selectedTransactionDate.toHourMinute()
        CustomTimePicker(
            initialHour = initHour,
            initialMinute = initMinute,
            onTimeSelected = { hour, minute ->
                onIntent(TransactionEditorIntent.OnTimeSelected(hour, minute))
                isTimePickerOpen = !isTimePickerOpen
            },
            onDismiss = { isTimePickerOpen = !isTimePickerOpen },
            onClear = { isTimePickerOpen = !isTimePickerOpen }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        ShowProgressIndicator(state.isLoading)
        if (state.isLoading) return
        Column {
            CustomListItem(
                title = stringResource(R.string.account),
                showLeading = false,
                trailingText = state.selectedAccount?.name.orEmpty(),
                onItemClick = { onIntent(TransactionEditorIntent.OnOpenAccountSheet) }
            )
            CustomListItem(
                title = stringResource(R.string.category),
                showLeading = false,
                trailingText = state.selectedCategory?.name.orEmpty(),
                onItemClick = { onIntent(TransactionEditorIntent.OnOpenCategorySheet) }
            )
            CustomTextInput(
                value = queryAmountRaw,
                onValueChange = {
                    if (validateBalanceInput(it)) {
                        onIntent(TransactionEditorIntent.OnAmountChange(it))
                    }
                },
                leadingText = stringResource(id = R.string.amount),
                textStyle = TextStyle(
                    color = colors.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.End
                ),
                leadingTextStyles = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = colors.onSurface
                ),
                trailingText = state.selectedAccount?.currency?.toCurrency(),
                colors = defaultTextFieldColors,
                isSmallItem = false
            )
            CustomListItem(
                title = stringResource(R.string.date),
                showLeading = false,
                trailingText = state.selectedTransactionDate.formatAsSimpleDate(),
                showTrailingIcon = false,
                onItemClick = { onIntent(TransactionEditorIntent.OnOpenDatePickerModal) }
            )
            CustomListItem(
                title = stringResource(R.string.time),
                showLeading = false,
                trailingText = state.selectedTransactionDate.formatAsSimpleTime(),
                showTrailingIcon = false,
                onItemClick = { onIntent(TransactionEditorIntent.OnOpenTimePickerModal) }
            )
            CustomTextInput(
                value = queryTransactionDescription,
                onValueChange = {
                    onIntent(TransactionEditorIntent.OnDescriptionChange(it))
                },
                placeholder = stringResource(id = R.string.comment),
                textStyle = TextStyle(
                    color = colors.onSurface,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400
                ),
                colors = defaultTextFieldColors,
                capitalizeFirstLetter = true,
                isSmallItem = false
            )
        }
    }
}

private fun validateBalanceInput(input: String): Boolean {
    if (input.isEmpty()) return true
    val regex = Regex("""^-?\d+([.,]\d{0,2})?$""")
    return regex.matches(input)
}