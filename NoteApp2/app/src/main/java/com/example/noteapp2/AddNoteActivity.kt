package com.example.noteapp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp2.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)

        binding.ivCheck.setOnClickListener() {
            val title = binding.etAddTitle.text.toString()
            val content = binding.etAddContent.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            finish()
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
        }

        binding.ivBack.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}