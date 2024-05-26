package com.fikrialfian.aplikasicatatan

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Notes() {
    val ctx = LocalContext.current
    val db = NoteDBHelper(ctx)
    val notes = remember { mutableStateOf(db.readNotes()) }
    LazyColumn(Modifier.padding(4.dp)) {
        item {
            InputTask(notes)
        }
        item {
            NoteList(notes)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTask(notes: MutableState<List<NotesModel>>) {
    val inputTask = remember { mutableStateOf("") }
    val ctx = LocalContext.current
    Column {
        TextField(
            value = inputTask.value,
            onValueChange = {
                inputTask.value = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = {
                if (inputTask.value.trim().isNotEmpty()) {
                    val db = NoteDBHelper(ctx)
                    db.createNote(inputTask.value.trim())
                    Toast.makeText(ctx, "sukses menambahkan task baru", Toast.LENGTH_SHORT).show()
                    inputTask.value = ""
                    notes.value = db.readNotes()
                } else {
                    Toast.makeText(ctx, "masukkan task dengan banar", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(3),
            colors = ButtonDefaults.buttonColors(Color.Blue)
        ) {
            Text("Save Task")
        }
    }
}

@Composable
fun NoteList(notes: MutableState<List<NotesModel>>) {
    val ctx = LocalContext.current
    Column(Modifier.padding(6.dp)) {
        Divider(Modifier.height(7.dp))
        Column(Modifier.padding(5.dp)) {
            Text(
                text = if (notes.value.isEmpty()) "Tidak ada task" else "Tasks:",
                fontWeight = FontWeight.Bold,
            )
        }
        Divider(Modifier.height(7.dp))
        Column(Modifier.padding(top = 11.dp)) {
            notes.value.map {
                Row(

                ) {
                    Text(
                        text = it.notes,
                        modifier = Modifier.fillMaxWidth(0.75F)
                    )
                    Button(
                        onClick = {
                            val helper = NoteDBHelper(ctx)
                            helper.deleteNote(it.id)
                            notes.value = helper.readNotes()
                        },
                        modifier = Modifier.wrapContentWidth(unbounded = true).padding(vertical = 4.dp, horizontal = 5.5.dp),
                        shape = RoundedCornerShape(3.dp),
                        colors = ButtonDefaults.buttonColors(Color.Red)
                    ) {
                        Text(
                            text = "DELETE",
                            fontSize = 12.sp,
                        )
                    }
                }
            }
        }
    }
}

data class NotesModel(
    val id: Int,
    val notes: String
)