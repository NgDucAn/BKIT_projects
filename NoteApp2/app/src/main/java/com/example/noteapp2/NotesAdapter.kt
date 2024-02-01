package com.example.noteapp2

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp2.databinding.NoteItemBinding

class NotesAdapter(private var notes: List<Note>, private val context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private var db: NotesDataBaseHelper = NotesDataBaseHelper(context)

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitle
        val content = binding.tvContent
        val update = binding.ivEdit
        val editTime = binding.tvEditTime
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.content.text = note.content
        holder.editTime.text = note.editTime

        holder.update.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Xóa ghi chú")
            alertDialogBuilder.setMessage("Bạn có chắc chắn muốn xóa ghi chú này?")

            alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
                db.deleteNote(note.id)
                refreshData(db.getAllNotes())
                Toast.makeText(holder.itemView.context, "Note Deleted", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialogBuilder.create().show()

            true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
