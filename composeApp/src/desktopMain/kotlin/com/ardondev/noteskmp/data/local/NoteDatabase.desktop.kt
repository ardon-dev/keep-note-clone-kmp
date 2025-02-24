package com.ardondev.noteskmp.data.local

import androidx.room.RoomDatabaseConstructor

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual object NoteDatabaseConstructor : RoomDatabaseConstructor<NoteDatabase> {
    actual override fun initialize(): NoteDatabase {
        throw NotImplementedError("Room no tiene soporte oficial para Desktop")
    }
}