package com.example.notesapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {

    private val _notes = mutableStateListOf<Note>()
    val notes: List<Note> get() = _notes

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
            _notes.add(0, Note(title, content, color = color))
        }
    }
}
