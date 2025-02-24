package com.ardondev.noteskmp.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.noteskmp.data.local.NoteDao
import com.ardondev.noteskmp.data.local.NoteEntity
import com.ardondev.noteskmp.ui.UiStateHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NotesViewModel(private val noteDao: NoteDao) : ViewModel() {

    /* UI State */

    private val _uiStateHandler = UiStateHandler<List<NoteEntity>>()
    val uiState = _uiStateHandler.uiState

    /* Get notes */

    fun getNotes() {
        viewModelScope.launch {
            _uiStateHandler.setLoading()
            noteDao.getAll()
                .catch { e ->
                    _uiStateHandler.setError(e.message.orEmpty())
                }
                .collect { list ->
                    _uiStateHandler.setSuccess(list)
                }
        }
    }

    /* Update note clipped value */

    fun clipNote(note: NoteEntity) {
        viewModelScope.launch {
            val clipped = note.clipped
            noteDao.update(note.copy(clipped = !clipped))
        }
    }

    /* Delete note */

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    init {
        getNotes()
    }

}