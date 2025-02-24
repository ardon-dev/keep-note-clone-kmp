package com.ardondev.noteskmp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ardondev.noteskmp.ui.util.NoteColor
import com.ardondev.noteskmp.ui.util.getColor

@Composable
fun NoteColorSelector(
    noteColors: List<NoteColor>,
    currentColor: NoteColor,
    onSelect: (NoteColor) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColorState = mutableStateOf(currentColor)

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
    ) {
        noteColors.forEach { noteColor ->
            NoteColorItem(
                noteColor = noteColor,
                selectedColorState = selectedColorState,
                onClick = {
                    onSelect(it)
                }
            )
            Spacer(Modifier.size(8.dp))
        }
    }
}

@Composable
fun NoteColorItem(
    noteColor: NoteColor,
    onClick: (NoteColor) -> Unit,
    selectedColorState: MutableState<NoteColor>
) {

    val currentColorIsSelected = selectedColorState.value.colorString == noteColor.colorString

    Box(
        modifier = Modifier
            .border(
                border = BorderStroke(
                    width = if (currentColorIsSelected) 2.dp else 0.dp,
                    color = if (currentColorIsSelected) Color.Black else Color.Transparent
                ),
                shape = CircleShape
            )
            .clickable {
                selectedColorState.value = noteColor
                onClick(noteColor)
            }
            .background(
                color = noteColor.getColor(),
                shape = CircleShape
            )
            .size(30.dp)
    )
}