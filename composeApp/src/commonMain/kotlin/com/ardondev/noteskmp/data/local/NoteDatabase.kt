package com.ardondev.noteskmp.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ardondev.noteskmp.data.local.dao.NoteDao
import com.ardondev.noteskmp.data.local.entity.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val DATABASE_NAME = "notes.db"

@Database(
    entities = [NoteEntity::class],
    version = 1
)
@ConstructedBy(NoteDatabaseConstructor::class)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

}

fun getNoteDatabase(builder: RoomDatabase.Builder<NoteDatabase>): NoteDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object NoteDatabaseConstructor: RoomDatabaseConstructor<NoteDatabase> {
    override fun initialize(): NoteDatabase
}