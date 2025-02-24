package com.ardondev.noteskmp.di

import com.ardondev.noteskmp.data.local.NoteDao
import com.ardondev.noteskmp.data.local.NoteDatabase
import com.ardondev.noteskmp.ui.screens.addNote.AddNoteViewModel
import com.ardondev.noteskmp.ui.screens.noteDetail.NoteDetailViewModel
import com.ardondev.noteskmp.ui.screens.notes.NotesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun commonModule() = module {
    single<NoteDao> { get<NoteDatabase>().getNoteDao() }
    viewModelOf(::NotesViewModel)
    viewModelOf(::AddNoteViewModel)
    viewModelOf(::NoteDetailViewModel)
}