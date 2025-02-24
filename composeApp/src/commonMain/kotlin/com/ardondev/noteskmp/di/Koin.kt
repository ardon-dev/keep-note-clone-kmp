package com.ardondev.noteskmp.di

import com.ardondev.noteskmp.platformModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(commonModule() + platformModule())
    }
}