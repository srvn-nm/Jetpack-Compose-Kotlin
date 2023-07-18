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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.todolist.R
import com.example.todolist.db.Note
import com.example.todolist.db.NoteDataBase

@Composable
fun MyNotes(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val notesList = rememberNotesList()

    Surface(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.navigate(R.id.action_myNotesFragment_to_newNoteFragment) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "New Task")
        }

        AndroidView(
            factory = { context ->
                RecyclerView(context).apply {
                    setHasFixedSize(true)
                    layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                    adapter = NotesAdapter(notesList)
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        LaunchedEffect(Unit) {
            val notes = NoteDataBase(LocalContext.current).getNoteDB().getAllNotes()
            notesList.addAll(notes)
        }
    }
}

@Composable
private fun rememberNotesList(): MutableStateList<Note> {
    return remember { mutableStateListOf() }
}
