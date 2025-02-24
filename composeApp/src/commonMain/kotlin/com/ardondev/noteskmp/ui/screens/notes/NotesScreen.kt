package com.ardondev.noteskmp.ui.screens.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ardondev.noteskmp.data.local.NoteEntity
import com.ardondev.noteskmp.ui.UiState
import com.ardondev.noteskmp.ui.util.Keep
import com.ardondev.noteskmp.ui.util.KeepFill
import com.ardondev.noteskmp.ui.util.NoteColor
import com.ardondev.noteskmp.ui.util.getColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = koinViewModel(),
    onAddNote: () -> Unit,
    onClickNote: (Long) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            AddNoteFloatingActionButton { onAddNote() }
        }
    ) {
        when (val state = uiState.value) {
            is UiState.Loading -> CircularProgressIndicator()
            is UiState.Error -> Text(state.message)
            is UiState.Success -> {
                val notes = state.data
                NotesScreenContent(
                    notes = notes,
                    onClickNote = { note ->
                        onClickNote(note.id!!)
                    },
                    onClipNote = { note ->
                        viewModel.clipNote(note)
                    }
                )
            }
        }
    }

}

@Composable
fun NotesScreenContent(
    notes: List<NoteEntity>,
    onClickNote: (NoteEntity) -> Unit,
    onClipNote: (NoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(notes) { note ->
            NoteItem(
                note = note,
                onClick = { onClickNote(note) },
                onClip = { onClipNote(note) }
            )
        }
    }
}


@Composable
fun AddNoteFloatingActionButton(
    onClick: () -> Unit
) {
    FloatingActionButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "Add a note"
        )
    }
}

@Composable
fun NoteItem(
    note: NoteEntity,
    onClick: (NoteEntity) -> Unit,
    onClip: (NoteEntity) -> Unit
) {
    val noteColor =
        NoteColor.entries.firstOrNull { it.colorString == note.color }?.getColor() ?: Color.White

    Card(
        onClick = { onClick(note) },
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = noteColor
            ),
            overlineContent = {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            },
            headlineContent = {
                Text(
                    text = note.text,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            },
            trailingContent = {
                IconButton(
                    onClick = { onClip(note) }
                ) {
                    Icon(
                        imageVector = if (note.clipped) KeepFill else Keep,
                        contentDescription = null
                    )
                }
            }
        )
    }
}