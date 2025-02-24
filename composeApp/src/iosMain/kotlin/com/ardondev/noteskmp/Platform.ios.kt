package com.ardondev.noteskmp

import com.ardondev.noteskmp.data.local.NoteDatabase
import com.ardondev.noteskmp.data.local.getDatabaseBuilder
import com.ardondev.noteskmp.data.local.getNoteDatabase
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun platformModule() = module {
    single<NoteDatabase> {
        val builder = getDatabaseBuilder()
        getNoteDatabase(builder)
    }
}