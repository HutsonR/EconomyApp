package com.backcube.economyapp.core.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BASE_HEIGHT = 70.dp
private val SMALL_HEIGHT = 56.dp

@Composable
fun CustomTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSmallItem: Boolean = true,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    leadingTextStyles: TextStyle = TextStyle(fontSize = 16.sp),
    colors: TextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    ),
    leadingText: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingText: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    capitalizeFirstLetter: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!leadingText.isNullOrEmpty()) {
                Text(
                    text = leadingText,
                    style = leadingTextStyles,
                    modifier = Modifier.padding(start = 16.dp, end = 8.dp)
                )
            }
            val height = if (isSmallItem) SMALL_HEIGHT else BASE_HEIGHT
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f).defaultMinSize(
                    minWidth = TextFieldDefaults.MinWidth,
                    minHeight = height
                ),
                enabled = enabled,
                singleLine = singleLine,
                textStyle = textStyle,
                colors = colors,
                placeholder = {
                    if (!placeholder.isNullOrEmpty()) Text(text = placeholder)
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                keyboardOptions = keyboardOptions.copy(
                    capitalization = if (capitalizeFirstLetter)
                        KeyboardCapitalization.Sentences
                    else
                        keyboardOptions.capitalization
                ),
                visualTransformation = visualTransformation
            )
            if (!trailingText.isNullOrEmpty()) {
                Text(
                    text = trailingText,
                    style = leadingTextStyles,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTextInput() {
    var text by remember { mutableStateOf("Какой-то очень длинный текст, который не помещается в инпут") }
    CustomTextInput(
        value = text,
        onValueChange = { text = it },
        placeholder = "Username",
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White.copy(alpha = 0.1f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f),
            cursorColor = Color(0xFF6200EE),
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            disabledPlaceholderColor = Color.Gray.copy(alpha = 0.5f)
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "User icon"
            )
        },
        leadingText = "User:"
    )
}

@Preview(showBackground = true, name = "С leadingText")
@Composable
fun PreviewCustomTextInputWithLeadingText() {
    var text by remember { mutableStateOf("Текст с leadingText") }
    CustomTextInput(
        value = text,
        onValueChange = { text = it },
        placeholder = "Введите текст",
        leadingText = "Label:",
        colors = OutlinedTextFieldDefaults.colors()
    )
}

@Preview(showBackground = true, name = "С leadingIcon")
@Composable
fun PreviewCustomTextInputWithLeadingIcon() {
    var text by remember { mutableStateOf("Текст с иконкой") }
    CustomTextInput(
        value = text,
        onValueChange = { text = it },
        placeholder = "Введите текст",
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email icon"
            )
        },
        colors = OutlinedTextFieldDefaults.colors()
    )
}

@Preview(showBackground = true, name = "С leadingText и leadingIcon")
@Composable
fun PreviewCustomTextInputWithLeadingTextAndIcon() {
    var text by remember { mutableStateOf("Текст с иконкой и текстом") }
    CustomTextInput(
        value = text,
        onValueChange = { text = it },
        placeholder = "Введите текст",
        leadingText = "Email:",
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email icon"
            )
        },
        colors = OutlinedTextFieldDefaults.colors()
    )
}