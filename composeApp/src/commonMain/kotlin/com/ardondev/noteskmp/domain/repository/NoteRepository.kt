package com.ardondev.noteskmp.domain.repository

import com.ardondev.noteskmp.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    suspend fun insert(note: Note): Long?

    fun getAll(): Flow<List<Note>>

    fun getById(noteId: Long): Flow<Note>

    suspend fun update(note: Note): Int

    suspend fun delete(note: Note)

}