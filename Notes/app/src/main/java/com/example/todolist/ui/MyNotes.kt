package com.example.todolist.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.todolist.db.Note
import com.example.todolist.db.NoteDataBase

@Composable
fun MyNotes(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val notesList = remember { mutableStateListOf<Note>() }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.navigate("newNote/") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "New Task")
        }


        NotesAdapter(notesList, navController)

    }

    LaunchedEffect(Unit) {
        val notes = NoteDataBase(context).getNoteDB().getAllNotes()
        notesList.addAll(notes)
    }
}

