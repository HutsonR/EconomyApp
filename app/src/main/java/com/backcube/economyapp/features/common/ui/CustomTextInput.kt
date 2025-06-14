package com.backcube.economyapp.features.common.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp),
    colors: TextFieldColors = TextFieldDefaults.colors(unfocusedIndicatorColor = Color.Transparent),
    leadingIcon: @Composable (() -> Unit)? = null,
    capitalizeFirstLetter: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column(modifier = modifier.fillMaxWidth()) {
        if (!label.isNullOrEmpty()) {
            Text(
                text = label,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(bottom = 4.dp, start = 20.dp)
            )
        }
        TextField(
            value = value,
            onValueChange = { new ->
                onValueChange(new)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            enabled = enabled,
            singleLine = singleLine,
            textStyle = textStyle,
            colors = colors,
            placeholder = {
                if (!placeholder.isNullOrEmpty()) {
                    Text(text = placeholder)
                }
            },
            leadingIcon = leadingIcon,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )
            },
            keyboardOptions = keyboardOptions.copy(
                capitalization = if (capitalizeFirstLetter)
                    KeyboardCapitalization.Sentences
                else
                    keyboardOptions.capitalization
            ),
            visualTransformation = visualTransformation
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
        label = "Введите логин",
        placeholder = "Username",
        colors = OutlinedTextFieldDefaults.colors(
            // Цвет текста
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,

            // Цвета контейнера (фон поля)
            focusedContainerColor = Color.White.copy(alpha = 0.1f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.05f),
            disabledContainerColor = Color.White.copy(alpha = 0.05f),

            // Цвет курсора
            cursorColor = Color(0xFF6200EE),

            // Цвета placeholder
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
    )
}