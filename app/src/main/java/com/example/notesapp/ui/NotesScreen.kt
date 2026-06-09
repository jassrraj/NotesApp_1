package com.example.notesapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.NotesViewModel
import com.example.notesapp.data.NoteEntity
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(viewModel: NotesViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val notes by viewModel.allNotes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { 
                    Text(
                        "My Notes",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                actions = {
                    IconButton(onClick = { /* Search functionality */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (notes.isEmpty()) {
                EmptyState()
            } else {
                NotesGrid(notes = notes, onDelete = { viewModel.deleteNote(it) })
            }
        }
    }

    if (showDialog) {
        AddNoteDialog(
            onDismiss = { showDialog = false },
            onAdd = { title, content, color ->
                viewModel.addNote(title, content, color)
                showDialog = false
            },
            availableColors = viewModel.noteColors
        )
    }
}

@Composable
fun NotesGrid(notes: List<NoteEntity>, onDelete: (NoteEntity) -> Unit) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalItemSpacing = 12.dp
    ) {
        items(notes) { note ->
            NoteCard(
                note = note,
                onDelete = { onDelete(note) }
            )
        }
    }
}

@Composable
fun NoteCard(note: NoteEntity, onDelete: () -> Unit) {
    val dateString = remember(note.timestamp) {
        val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        sdf.format(Date(note.timestamp))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(note.color)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black.copy(alpha = 0.7f),
                maxLines = 10
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = dateString,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Black.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "No notes yet",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Tap + to create your first note",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}
