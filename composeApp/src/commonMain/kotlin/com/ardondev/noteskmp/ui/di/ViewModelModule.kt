package com.ardondev.noteskmp.ui.di

import com.ardondev.noteskmp.ui.screens.addNote.AddNoteViewModel
import com.ardondev.noteskmp.ui.screens.noteDetail.NoteDetailViewModel
import com.ardondev.noteskmp.ui.screens.notes.NotesViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::NotesViewModel)
    viewModelOf(::AddNoteViewModel)
    viewModelOf(::NoteDetailViewModel)
}