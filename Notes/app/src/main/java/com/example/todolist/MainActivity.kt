package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.db.Note
import com.example.todolist.ui.MyNotes
import com.example.todolist.ui.NewNote
import com.example.todolist.ui.NoteItem
import com.example.todolist.ui.theme.TodoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                // A surface container using the 'background' color from the theme
                NoteSetUp()
            }
        }
    }
}

@Composable
fun NoteSetUp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "notes") {
        composable("notes") {
            MyNotes(navController)
        }
        composable(
            "note/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.ParcelableType(Note::class.java)
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Note>("noteId")?.let {
                NoteItem(
                    navController = navController,
                    note = it
                )
            }
        }
        composable("newNote/{noteId}",
            arguments = listOf(navArgument("noteId") {
                type = NavType.ParcelableType(Note::class.java)
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getParcelable<Note>("noteId")?.let {
                val context = LocalContext.current
                NewNote(navController, it, context)
            }
        }
    }
}
