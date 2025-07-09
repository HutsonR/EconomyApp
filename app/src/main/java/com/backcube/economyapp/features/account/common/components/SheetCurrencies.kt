package com.backcube.economyapp.features.account.common.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.backcube.economyapp.R
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.core.ui.theme.EconomyAppTheme
import com.backcube.economyapp.core.ui.utils.toCurrency
import com.backcube.economyapp.core.ui.utils.toDisplayNameId
import com.backcube.economyapp.core.ui.utils.toImageSource
import com.backcube.economyapp.domain.utils.CurrencyIsoCode

@Composable
fun SheetCurrencies(
    currencies: List<CurrencyIsoCode>,
    onCurrencyClick: (CurrencyIsoCode) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        itemsIndexed(currencies, key = { _, item -> item.name }) { index, currency ->
            val displayName = context.getText(currency.toDisplayNameId())
            CustomListItem(
                title = "$displayName ${currency.toCurrency()}",
                leadingContent = {
                    Image(
                        painter = painterResource(currency.toImageSource()),
                        contentDescription = null
                    )
                },
                showTrailingIcon = false,
                showSeparator = index != currencies.lastIndex,
                onItemClick = { onCurrencyClick(currency) }
            )
        }

        item {
            CustomListItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.errorContainer),
                title = context.getString(R.string.cancel),
                titleColor = MaterialTheme.colorScheme.onErrorContainer,
                leadingContent = {
                    Image(
                        painter = painterResource(R.drawable.ic_cancel),
                        contentDescription = null
                    )
                },
                showSeparator = false,
                showTrailingIcon = false,
                onItemClick = onCancel
            )
        }
    }
}

@Preview
@Composable
fun SheetCurrenciesPreview() {
    EconomyAppTheme {
        SheetCurrencies(
            currencies = CurrencyIsoCode.entries,
            onCurrencyClick = {  },
            onCancel = {  }
        )
    }
}