@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.noteskmp.ui.screens.noteDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.IconButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ardondev.noteskmp.ui.UiState
import com.ardondev.noteskmp.ui.components.CustomTextField
import com.ardondev.noteskmp.ui.components.NoteColorSelector
import com.ardondev.noteskmp.ui.util.Keep
import com.ardondev.noteskmp.ui.util.KeepFill
import com.ardondev.noteskmp.ui.util.NoteColor
import com.ardondev.noteskmp.ui.util.getColor
import com.ardondev.noteskmp.ui.util.noteColors
import kotlinx.coroutines.delay
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteDetailViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Error -> Text(state.message)
        is UiState.Success -> {
            val currentNote = state.data

            // Default data
            LaunchedEffect(currentNote) {
                viewModel.setTitle(currentNote.title)
                viewModel.setText(currentNote.text)
                viewModel.setColor(currentNote.color)
                viewModel.setClipped(currentNote.clipped)
            }

            // Content
            NoteDetailScreenContent(
                titleState = viewModel.title,
                textState = viewModel.text,
                clippedState = viewModel.clipped,
                colorState = viewModel.color,
                onSaveNote = {
                    println("Nota guardada")
                    viewModel.updateNote(currentNote)
                },
                onClipNote = {
                    viewModel.setClipped(!viewModel.clipped.value)
                    viewModel.updateNote(currentNote)
                }
            )
        }
    }

}

@Composable
private fun NoteDetailScreenContent(
    titleState: MutableState<String>,
    textState: MutableState<String>,
    clippedState: MutableState<Boolean>,
    colorState: MutableState<String>,
    onSaveNote: () -> Unit,
    onClipNote: () -> Unit
) {

    val color = NoteColor.entries.firstOrNull { it.colorString == colorState.value }?.getColor()
        ?: Color.White

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Color
            NoteColorSelector(
                noteColors = noteColors,
                currentColor = NoteColor.entries.firstOrNull { it.colorString == colorState.value }
                    ?: NoteColor.WHITE,
                onSelect = { noteColor ->
                    colorState.value = noteColor.colorString
                    onSaveNote()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            IconButton(
                onClick = onClipNote,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (clippedState.value) KeepFill else Keep,
                    contentDescription = null
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    color = color,
                    shape = MaterialTheme.shapes.small
                )
        ) {

            // Title
            val debouncedTitle = remember { mutableStateOf(titleState.value) }
            LaunchedEffect(titleState.value) {
                delay(1000)
                if (debouncedTitle.value != titleState.value) {
                    debouncedTitle.value = titleState.value
                    onSaveNote()
                }
            }
            CustomTextField(
                value = titleState,
                textStyle = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )


            // Text
            val debouncedText = remember { mutableStateOf(textState.value) }
            LaunchedEffect(textState.value) {
                delay(1000)
                if (debouncedText.value != textState.value) {
                    debouncedText.value = textState.value
                    onSaveNote()
                }
            }
            CustomTextField(
                value = textState,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }

    }

}