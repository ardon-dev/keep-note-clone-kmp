@file:OptIn(ExperimentalMaterialApi::class)

package com.ardondev.noteskmp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.ardondev.noteskmp.ui.screens.addNote.ColorMenuItem
import com.ardondev.noteskmp.ui.util.NoteColor

@ExperimentalMaterial3Api
@Composable
fun ColorDropDownMenu(
    colorState: MutableState<String>,
    colors: List<NoteColor>
) {

    val expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        CustomTextField(
            value = colorState,
            readOnly = true,
            label = "Color",
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded.value)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = {
                expanded.value = false
            }
        ) {
            colors.forEach { color ->
                ColorMenuItem(color) {
                    colorState.value = color.colorString
                    expanded.value = false
                }
            }
        }
    }
}