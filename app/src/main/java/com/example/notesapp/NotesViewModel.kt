package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notesapp.data.NoteEntity
import com.example.notesapp.data.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes: Flow<List<NoteEntity>> = repository.allNotes

    // Predefined pastel colors for notes
    val noteColors = listOf(
        0xFFFFF9C4, // Light Yellow
        0xFFFFCCBC, // Light Orange
        0xFFC8E6C9, // Light Green
        0xFFB3E5FC, // Light Blue
        0xFFF8BBD0, // Light Pink
        0xFFD1C4E9, // Light Purple
        0xFFCFD8DC  // Blue Grey
    ).map { it.toInt() }

    fun addNote(title: String, content: String, color: Int) {
        if (title.isNotBlank()) {
            viewModelScope.launch {
                val newNote = NoteEntity(
                    title = title,
                    content = content,
                    timestamp = System.currentTimeMillis(),
                    color = color
                )
                repository.insert(newNote)
            }
        }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.delete(note)
        }
    }
}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
