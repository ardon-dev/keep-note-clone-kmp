package com.ardondev.noteskmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Notes KMP",
    ) {
        App()
    }
}