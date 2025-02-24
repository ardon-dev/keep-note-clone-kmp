package com.ardondev.noteskmp

import androidx.compose.ui.window.ComposeUIViewController
import com.ardondev.noteskmp.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin(appDeclaration = {})
    }
) { App() }
