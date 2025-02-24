@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.noteskmp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ardondev.noteskmp.data.di.databaseModule
import com.ardondev.noteskmp.data.di.repositoryModule
import com.ardondev.noteskmp.ui.MainScreen
import com.ardondev.noteskmp.ui.di.viewModelModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

@Composable
@Preview
fun App() { MainScreen() }

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(databaseModule + repositoryModule + viewModelModule + platformModule())
    }
}