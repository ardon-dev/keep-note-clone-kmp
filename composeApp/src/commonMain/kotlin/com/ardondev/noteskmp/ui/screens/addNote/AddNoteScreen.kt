@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.ardondev.noteskmp.ui.screens.addNote

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ardondev.noteskmp.ui.components.CustomTextField
import com.ardondev.noteskmp.ui.components.NoteColorSelector
import com.ardondev.noteskmp.ui.util.NoteColor
import com.ardondev.noteskmp.ui.util.getColor
import com.ardondev.noteskmp.ui.util.noteColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddNoteScreen(
    viewModel: AddNoteViewModel = koinViewModel(),
    onNoteAdded: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        // Screen content
        AddNoteScreenContent(
            titleState = viewModel.title,
            textState = viewModel.text,
            colorState = viewModel.color,
            formIsCompleted = viewModel.formCompleted.value,
            onAddNote = {
                viewModel.addNote()
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Success add note dialog
        AnimatedVisibility(viewModel.insertNoteWasSuccess.value) {
            CustomAlertDialog(
                message = "Note added successfully",
                onEvent = {
                    viewModel.clearData()
                    onNoteAdded()
                }
            )
        }

        // Error add note dialog
        val showError = !viewModel.insertNoteWasSuccess.value && viewModel.error.value != null
        AnimatedVisibility(showError) {
            CustomAlertDialog(
                message = "${viewModel.error.value}",
                onEvent = {
                    viewModel.setInsertNoteWasSuccess(false)
                    viewModel.setError(null)
                }
            )
        }

    }

}

@Composable
fun AddNoteScreenContent(
    titleState: MutableState<String>,
    textState: MutableState<String>,
    colorState: MutableState<String>,
    formIsCompleted: Boolean,
    onAddNote: () -> Unit,
    modifier: Modifier
) {

    val currentNoteColor =
        NoteColor.entries.firstOrNull { it.colorString == colorState.value } ?: NoteColor.WHITE

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        AddNoteForm(titleState, textState, colorState)
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = currentNoteColor.getColor(),
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            onClick = onAddNote,
            enabled = formIsCompleted,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Add note")
        }
    }
}

@Composable
fun AddNoteForm(
    titleState: MutableState<String>,
    textState: MutableState<String>,
    colorState: MutableState<String>
) {

    val currentNoteColor =
        NoteColor.entries.firstOrNull { it.colorString == colorState.value } ?: NoteColor.WHITE

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Color
        NoteColorSelector(
            noteColors = noteColors,
            currentColor = currentNoteColor,
            onSelect = {
                colorState.value = it.colorString
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = currentNoteColor.getColor(),
                    shape = MaterialTheme.shapes.medium
                )
        ) {
            // Title
            CustomTextField(
                value = titleState,
                placeholder = {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
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
            CustomTextField(
                value = textState,
                placeholder = {
                    Text(
                        text = "Text...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
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

@Composable
fun ColorMenuItem(
    color: NoteColor,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(
                        color = color.getColor(),
                        shape = MaterialTheme.shapes.small
                    )
            )
        },
        onClick = onClick
    )
}

@Composable
fun CustomAlertDialog(
    message: String,
    onEvent: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    Dialog(onDismiss) {
        Card {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.size(16.dp))
                Button(onEvent) { Text("Ok") }
            }
        }
    }
}
