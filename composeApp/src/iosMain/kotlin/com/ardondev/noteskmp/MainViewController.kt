package com.ardondev.noteskmp

import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(appDeclaration = {})
    }
) { App() }
