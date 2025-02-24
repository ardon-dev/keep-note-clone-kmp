@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.noteskmp.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ardondev.noteskmp.ui.screens.addNote.AddNoteScreen
import com.ardondev.noteskmp.ui.screens.noteDetail.NoteDetailScreen
import com.ardondev.noteskmp.ui.screens.notes.NotesScreen

@Composable
fun MainScreen() {

    val colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    val navController = rememberNavController()
    val appBarTitle = rememberSaveable { mutableStateOf("") }
    navController.addOnDestinationChangedListener { _, destination, _ ->
        appBarTitle.value = when (destination.route) {
            NoteNavigation.Notes.name -> NoteNavigation.Notes.title
            NoteNavigation.AddNote.name -> NoteNavigation.AddNote.title
            NoteNavigation.NoteDetail.routeWithParams -> NoteNavigation.NoteDetail.title
            else -> ""
        }
    }

    MaterialTheme(
        colorScheme = colorScheme
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    title = appBarTitle.value,
                    showArrowBack = navController.currentDestination?.route != NoteNavigation.Notes.name,
                    onBack = { navController.navigateUp() }
                )
            },
            modifier = Modifier
                .fillMaxSize()
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = NoteNavigation.Notes.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                    )
            ) {
                composable(route = NoteNavigation.Notes.name) {
                    NotesScreen(
                        onAddNote = {
                            navController.navigate(NoteNavigation.AddNote.name)
                        },
                        onClickNote = { noteId ->
                            print(noteId)
                            navController.navigate(
                                route = NoteNavigation.NoteDetail.name + "/$noteId"
                            )
                        }
                    )
                }
                composable(route = NoteNavigation.AddNote.name) {
                    AddNoteScreen(
                        onNoteAdded = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(
                    route = NoteNavigation.NoteDetail.routeWithParams,
                    arguments = listOf(
                        navArgument("noteId") { type = NavType.LongType }
                    )
                ) { backStackEntry ->
                    val noteId = backStackEntry.arguments?.getLong("noteId")
                    NoteDetailScreen(noteId!!)
                }
            }
        }

    }

}

@Composable
fun AppTopBar(
    title: String,
    showArrowBack: Boolean,
    onBack: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            if (showArrowBack) {
                IconButton(onBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        title = { Text(title) }
    )
}

enum class NoteNavigation(
    val title: String,
    val routeWithParams: String = ""
) {
    Notes(title = "My notes"),
    AddNote(title = "Add note"),
    NoteDetail(
        title = "Note detail",
        routeWithParams = "NoteDetail/{noteId}"
    )
}