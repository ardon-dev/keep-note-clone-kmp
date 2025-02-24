package com.ardondev.noteskmp.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<NoteDatabase> {
    val appCtx = context.applicationContext
    val dbFile = appCtx.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<NoteDatabase>(
        context = appCtx,
        name = dbFile.absolutePath
    )
}