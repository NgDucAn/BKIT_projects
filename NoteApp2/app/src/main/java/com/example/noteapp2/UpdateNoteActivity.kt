package com.example.noteapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp2.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateNoteBinding
    private lateinit var db: NotesDataBaseHelper
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish()
            return
        }
        val note = db.getNoteByID(noteId)
        binding.etEditTitle.setText(note.title)
        binding.etEditContent.setText(note.content)
        binding.ivEditCheck.setOnClickListener() {
            val newTitle = binding.etEditTitle.text.toString()
            val newContent = binding.etEditContent.text.toString()
            val updateNote = Note(noteId, newTitle, newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
        binding.ivBack.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}