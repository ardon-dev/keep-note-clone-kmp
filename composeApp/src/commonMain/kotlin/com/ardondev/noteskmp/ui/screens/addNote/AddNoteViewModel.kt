package com.ardondev.noteskmp.ui.screens.addNote

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ardondev.noteskmp.domain.repository.NoteRepository
import com.ardondev.noteskmp.domain.model.Note
import com.ardondev.noteskmp.ui.util.NoteColor
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AddNoteViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    /* Note form states */

    var title = mutableStateOf("")
        private set

    fun setTitle(value: String?) {
        value?.let { title.value = it }
    }

    var text = mutableStateOf("")
        private set

    fun setText(value: String?) {
        value?.let { text.value = it }
    }

    var color = mutableStateOf(NoteColor.WHITE.colorString)
        private set

    fun setColor(value: String?) {
        value?.let { color.value = it }
    }

    fun clearData() {
        title.value = ""
        color.value = ""
        text.value = ""
        insertNoteWasSuccess.value = false
        error.value = null
    }

    val formCompleted = derivedStateOf {
        if (title.value.isBlank()) return@derivedStateOf false
        if (text.value.isBlank()) return@derivedStateOf false
        if (color.value.isBlank()) return@derivedStateOf false
        true
    }

    /* Add note result **/

    var error = mutableStateOf<String?>(null)
        private set

    fun setError(value: String?) {
        error.value = value
    }

    var insertNoteWasSuccess = mutableStateOf(false)
        private set

    fun setInsertNoteWasSuccess(value: Boolean) {
        insertNoteWasSuccess.value = value
    }

    fun addNote() {
        viewModelScope.launch {
            try {
                val note = Note(
                    title = title.value,
                    text = text.value,
                    color = color.value,
                    modificationDate = Clock.System.now()
                        .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
                )
                val result = noteRepository.insert(note)
                if (result != null) {
                    error.value = null
                    insertNoteWasSuccess.value = true
                } else {
                    insertNoteWasSuccess.value = false
                    error.value = "No se agregó la nota."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                insertNoteWasSuccess.value = false
                error.value = e.message.orEmpty()
            }
        }

    }

}