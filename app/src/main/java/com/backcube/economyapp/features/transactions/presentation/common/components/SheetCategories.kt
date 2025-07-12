package com.backcube.economyapp.features.transactions.presentation.common.components

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
import com.backcube.economyapp.R
import com.backcube.economyapp.core.ui.components.CustomListItem
import com.backcube.economyapp.domain.models.categories.CategoryModel

@Composable
fun SheetCategories(
    currencies: List<CategoryModel>,
    onCurrencyClick: (CategoryModel) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        itemsIndexed(currencies, key = { _, item -> item.name }) { index, item ->
            CustomListItem(
                title = item.name,
                showLeading = false,
                showTrailingIcon = false,
                showSeparator = index != currencies.lastIndex,
                onItemClick = { onCurrencyClick(item) }
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