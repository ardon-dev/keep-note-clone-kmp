package com.ardondev.noteskmp.ui.util

import androidx.compose.ui.graphics.Color

enum class NoteColor(val colorString: String) {
    WHITE(colorString = "white"),
    RED(colorString = "red"),
    GREEN(colorString = "green"),
    BLUE(colorString = "blue"),
}

fun NoteColor.getColor(): Color {
    return when (this) {
        NoteColor.GREEN -> Color(0xFFDAE5D0)
        NoteColor.BLUE -> Color(0xFFD6E5FA)
        NoteColor.RED -> Color(0xFFFAD4D4)
        NoteColor.WHITE -> Color(0xFFFFFFFF)
    }
}

val noteColors = listOf(NoteColor.WHITE, NoteColor.RED, NoteColor.GREEN, NoteColor.BLUE)