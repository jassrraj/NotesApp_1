package com.example.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.notesapp.data.NoteDatabase
import com.example.notesapp.data.NoteRepository
import com.example.notesapp.ui.NotesScreen

/**
 * The main activity which serves as the entry point for the application.
 */
class MainActivity : ComponentActivity() {

    // Initialize the Database and Repository
    private val database by lazy { NoteDatabase.getDatabase(this) }
    private val repository by lazy { NoteRepository(database.noteDao()) }

    // Initialize the ViewModel using the factory
    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotesScreen(viewModel = viewModel)
                }
            }
        }
    }
}
