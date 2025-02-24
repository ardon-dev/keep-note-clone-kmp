package com.ardondev.noteskmp

import android.app.Application
import com.ardondev.noteskmp.di.initKoin
import org.koin.android.ext.koin.androidContext

class NotesKMP: Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            appDeclaration = {
                androidContext(this@NotesKMP)
            }
        )

    }

}