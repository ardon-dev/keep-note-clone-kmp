package com.ardondev.noteskmp.ui.components

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun CustomTextField(
    value: MutableState<String>,
    label: String? = null,
    readOnly: Boolean = false,
    placeholder: @Composable() (() -> Unit)? = null,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    colors: TextFieldColors? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    afterValueChanges: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TextField(
        value = value.value,
        label = label?.let { { Text(it) } },
        readOnly = readOnly,
        textStyle = textStyle,
        placeholder = placeholder,
        onValueChange = {
            value.value = it
            afterValueChanges()
        },
        shape = MaterialTheme.shapes.medium,
        trailingIcon = trailingIcon,
        colors = colors ?: TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = modifier
    )
}