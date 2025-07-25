package com.backcube.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomOption(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    textColor: Color? = null,
    radioButtonColors: RadioButtonColors? = null,
    trailingComposable: (@Composable () -> Unit)? = null,
    onSelect: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val actualTextColor = textColor ?: colors.onSurface
    val actualRadioColors = radioButtonColors ?: RadioButtonDefaults.colors(
        selectedColor = colors.primary,
        unselectedColor = colors.onSurface
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = actualRadioColors
        )
        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            color = actualTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        if (trailingComposable != null) {
            Spacer(modifier = Modifier.width(8.dp))
            trailingComposable()
        }
    }
}

@Preview
@Composable
fun CustomOptionPreview() {
    CustomOption(
        text = "Option very very very very very very very very BIIIIIIIIIIIIIIIIIIIIG",
        selected = true,
        onSelect = {}
    )
}


@Preview
@Composable
fun CustomOptionWithComposablePreview() {
    CustomOption(
        text = "Option very very very very very very very very BIIIIIIIIIIIIIIIIIIIIG",
        selected = true,
        onSelect = {},
        trailingComposable = {
            Text(text = "Trailing")
        }

    )
}