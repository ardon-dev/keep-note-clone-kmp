package com.ardondev.noteskmp.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.noteskmp.domain.repository.NoteRepository
import com.ardondev.noteskmp.domain.model.Note
import com.ardondev.noteskmp.ui.UiStateHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    /* UI State */

    private val _uiStateHandler = UiStateHandler<List<Note>>()
    val uiState = _uiStateHandler.uiState

    /* Get notes */

    fun getNotes() {
        viewModelScope.launch {
            _uiStateHandler.setLoading()
            noteRepository.getAll()
                .catch { e ->
                    _uiStateHandler.setError(e.message.orEmpty())
                }
                .collect { list ->
                    _uiStateHandler.setSuccess(list)
                }
        }
    }

    /* Update note clipped value */

    fun clipNote(note: Note) {
        viewModelScope.launch {
            val clipped = note.clipped
            noteRepository.update(note.copy(clipped = !clipped))
        }
    }

    /* Delete note */

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteRepository.delete(note)
        }
    }

    init {
        getNotes()
    }

}