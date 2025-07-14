package com.backcube.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.backcube.ui.R

@Composable
fun ShowAlertDialog(
    modifier: Modifier = Modifier,
    alertData: AlertData = AlertData(),
    onCancelButtonClick: () -> Unit = {},
    onActionButtonClick: () -> Unit
) {
    val message = alertData.messageString.ifEmpty {
        stringResource(id = alertData.message)
    }
    AlertDialog(
        title = {
            Text(
                text = stringResource(id = alertData.title),
                modifier = Modifier
            )
        },
        text = {
            Text(
                text = message,
                modifier = Modifier
            )
        },
        dismissButton = if (alertData.isCancelable) {
            {
                TextButton(
                    onClick = onCancelButtonClick,
                    modifier = Modifier,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                        disabledContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    content = {
                        Text(
                            text = stringResource(id = alertData.secondButtonTitle),
                            modifier = Modifier
                        )
                    }
                )
            }
        } else null,
        confirmButton = {
            TextButton(
                onClick = onActionButtonClick,
                modifier = Modifier,
                shape = MaterialTheme.shapes.medium,
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.onSurface
                ),
                content = {
                    Text(
                        text = stringResource(id = alertData.actionButtonTitle),
                        modifier = Modifier
                    )
                }
            )
        },
        onDismissRequest = onActionButtonClick,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier.testTag("debugTag:InfoDialog"),
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        shape = MaterialTheme.shapes.medium,
    )
}

data class AlertData(
    val title: Int = R.string.alert_base_title,
    val message: Int = R.string.alert_base_description,
    val messageString: String = "",
    val actionButtonTitle: Int = R.string.understand,
    val secondButtonTitle: Int = R.string.cancel,
    val isCancelable: Boolean = false,
    val action: (() -> Unit)? = null
)