package com.ardondev.noteskmp.data.di

import com.ardondev.noteskmp.data.local.dao.NoteDao
import com.ardondev.noteskmp.data.repository.NoteRepositoryImpl
import com.ardondev.noteskmp.domain.repository.NoteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {

    /* Note */

    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    singleOf(::provideNoteRepository)

}