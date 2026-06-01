package com.example.notesapp

/**
 * Data class representing a single note.
 */
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int = 0xFFFFFFFF.toInt()
)
