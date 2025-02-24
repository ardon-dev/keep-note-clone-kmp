package com.ardondev.noteskmp.data.di

import com.ardondev.noteskmp.data.local.NoteDatabase
import com.ardondev.noteskmp.data.local.dao.NoteDao
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {

    /* Note DAO */

    fun provideNoteDao(database: NoteDatabase): NoteDao {
        return database.getNoteDao()
    }
    singleOf(::provideNoteDao)

}