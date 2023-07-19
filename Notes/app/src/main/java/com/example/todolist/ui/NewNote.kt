package com.example.todolist.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todolist.db.Note
import com.example.todolist.db.NoteDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNote(navController: NavController, note: Note? = null, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    val newNoteTitleState = remember { mutableStateOf(note?.title ?: "") }
    val newNoteTextState = remember { mutableStateOf(note?.note ?: "") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = newNoteTitleState.value,
                onValueChange = { newNoteTitleState.value = it },
                label = { Text(text = "Note Title") }
            )
            OutlinedTextField(
                value = newNoteTextState.value,
                onValueChange = { newNoteTextState.value = it },
                label = { Text(text = "Note Text") }
            )
            Button(onClick = {
                val newNoteTitle = newNoteTitleState.value.trim()
                val newNoteText = newNoteTextState.value.trim()

                if (newNoteTitle.isEmpty()) {
                    Toast.makeText(context, "Please write your new note title!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(context, newNoteTitle, Toast.LENGTH_SHORT).show()

                    try {
                        coroutineScope.launch(Dispatchers.Main) {
                            val newNote = Note(
                                title = newNoteTitle,
                                note = newNoteText
                            )

                            val noteDB = NoteDataBase(context).getNoteDB()

                            if (note == null) {
                                noteDB.addNote(newNote)
                            } else {
                                newNote.id = note.id
                                noteDB.updateNote(newNote)
                            }
                        }

                        navController.navigate("myNotesFragment")
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
fun DeleteNoteAlertDialog(note: Note, onConfirm: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(
                    text = "Are you sure that you need to delete ${note.title}?",
                    fontSize = 16.sp,
                )
            },
            text = {
                Text(
                    text = "There is no way to undo this operation!",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm
                ) {
                    Text(text = "Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}

@Composable
fun NewNoteOptionsMenu(navController: NavController, note: Note?, context: Context) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val noteDB = NoteDataBase(context).getNoteDB()
            note?.let { noteDB.deleteNote(it) }
        }

        navController.navigate("myNotes")
    }
}