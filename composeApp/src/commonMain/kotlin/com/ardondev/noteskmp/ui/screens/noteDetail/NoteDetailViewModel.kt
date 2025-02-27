package com.ardondev.noteskmp.ui.screens.noteDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.noteskmp.domain.repository.NoteRepository
import com.ardondev.noteskmp.domain.model.Note
import com.ardondev.noteskmp.ui.UiStateHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class NoteDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val noteRepository: NoteRepository
) : ViewModel() {

    /* Note */

    private val noteId: Long = savedStateHandle.get<Long>("noteId")!!

    var title = mutableStateOf("")
        private set

    fun setTitle(value: String) {
        title.value = value
    }

    var text = mutableStateOf("")
        private set

    fun setText(value: String) {
        text.value = value
    }

    var color = mutableStateOf("")
        private set

    fun setColor(value: String) {
        color.value = value
    }

    var clipped = mutableStateOf(false)
        private set

    fun setClipped(value: Boolean) {
        clipped.value = value
    }

    private val _uiStateHandler = UiStateHandler<Note>()
    val uiState = _uiStateHandler.uiState

    fun getNote() {
        viewModelScope.launch {
            _uiStateHandler.setLoading()
            noteRepository.getById(noteId)
                .catch { error -> _uiStateHandler.setError(error.message.orEmpty()) }
                .collect { note -> _uiStateHandler.setSuccess(note) }
        }
    }

    /* Update note */

    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteRepository.update(
                note.copy(
                    title = title.value,
                    text = text.value,
                    color = color.value,
                    clipped = clipped.value,
                    modificationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
            )
        }
    }

    init {
        getNote()
    }

}