package com.example.noteapp2

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDataBaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDataBaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.rvListNote.layoutManager = LinearLayoutManager(this)
        binding.rvListNote.adapter = notesAdapter

        binding.fbAdd.setOnClickListener() {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

        binding.ivSearch.setOnClickListener() {
            binding.cvSearch.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}