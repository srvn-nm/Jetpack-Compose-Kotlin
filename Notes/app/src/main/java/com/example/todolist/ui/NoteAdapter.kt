package com.example.todolist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.db.Note

@Composable
fun NotesAdapter(notesList: List<Note>, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(notesList) { note ->
            NoteItem(note = note, navController = navController)
        }
    }
}

@Composable
fun NoteItem(note: Note, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.NewNote.route(note))
            }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            note.title?.let { Text(text = it) }
            note.note?.let { Text(text = it) }
        }
    }
}
