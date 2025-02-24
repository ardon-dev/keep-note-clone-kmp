package com.ardondev.noteskmp

import android.os.Build
import com.ardondev.noteskmp.data.local.NoteDatabase
import com.ardondev.noteskmp.data.local.getDatabaseBuilder
import com.ardondev.noteskmp.data.local.getNoteDatabase
import org.koin.dsl.module

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformModule() = module {
    single<NoteDatabase> {
        val builder = getDatabaseBuilder(context = get())
        getNoteDatabase(builder)
    }
}