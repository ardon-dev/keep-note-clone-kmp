package com.ardondev.noteskmp.data.repository

import com.ardondev.noteskmp.data.local.dao.NoteDao
import com.ardondev.noteskmp.data.toEntity
import com.ardondev.noteskmp.data.toNote
import com.ardondev.noteskmp.domain.repository.NoteRepository
import com.ardondev.noteskmp.domain.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteRepositoryImpl(
    private val noteDao: NoteDao
) : NoteRepository {

    override suspend fun insert(note: Note): Long? {
        return noteDao.insert(note.toEntity())
    }

    override fun getAll(): Flow<List<Note>> {
        return noteDao.getAll().map { list ->
            list.map { noteEntity ->
                noteEntity.toNote()
            }
        }
    }

    override fun getById(noteId: Long): Flow<Note> {
        return noteDao.getById(noteId).map { it.toNote() }
    }

    override suspend fun update(note: Note): Int {
        return noteDao.update(note.toEntity())
    }

    override suspend fun delete(note: Note) {
        return noteDao.delete(note.toEntity())
    }

}