package com.backcube.ui.components.date

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.backcube.ui.R
import com.backcube.ui.utils.LocalAppContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    selectedDate: Long?,
    mode: DateMode,
    onDateSelected: (DateMode, Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalAppContext.current
    val state = rememberDatePickerState(
        initialSelectedDateMillis = selectedDate,
        yearRange = 2000..2100
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onBackground)
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Очистить (только сбрасывает дату)
                TextButton(
                    onClick = {
                        onDateSelected(mode, null)
                    }
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onSurface,
                        text = context.getString(R.string.clear)
                    )
                }

                Row {
                    TextButton(onClick = onDismiss) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface,
                            text = context.getString(R.string.cancel)
                        )
                    }

                    TextButton(
                        onClick = {
                            onDateSelected(mode, state.selectedDateMillis)
                            onDismiss()
                        },
                        enabled = state.selectedDateMillis != null
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface,
                            text = context.getString(R.string.ok_big)
                        )
                    }
                }
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        DatePicker(
            state = state,
            title = null,
            headline = null,
            showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                selectedDayContainerColor = MaterialTheme.colorScheme.primary,
                selectedDayContentColor = MaterialTheme.colorScheme.onSurface,
                todayDateBorderColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContainerColor = MaterialTheme.colorScheme.onSurface,
                selectedYearContentColor = MaterialTheme.colorScheme.onSurface,
                todayContentColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}