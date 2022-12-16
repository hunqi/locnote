package com.rs.locnote

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.rs.locnote.dao.NoteDatabaseHelper

class NoteAdapter(private val notes: MutableList<Note>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private lateinit var dbHelper: NoteDatabaseHelper

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.noteTitle)
        val noteFirstLine: TextView = view.findViewById(R.id.noteFirstLine)
        val noteUpdateTime: TextView = view.findViewById(R.id.noteUpdateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(this.javaClass.simpleName, "onCreateViewHolder(...)")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        val viewHolder = ViewHolder(view)

        dbHelper = NoteDatabaseHelper(parent.context, "locnote.db", 1)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.absoluteAdapterPosition
            val note = notes[position]
            val intent = Intent(parent.context, SecondActivity::class.java)
            intent.putExtra("title", note.title)
            startActivity(parent.context, intent, null)
        }
        viewHolder.itemView.setOnLongClickListener{
            val position = viewHolder.absoluteAdapterPosition
            val note = notes[position]

            AlertDialog.Builder(parent.context).apply {
                setTitle("删除")
                setMessage(String.format("删除%s?", note.title))
                setCancelable(false)
                setPositiveButton("OK") { _, _ ->
                    val db = dbHelper.writableDatabase
                    db.delete("Note", "title = ?", arrayOf(note.title))
                    parent.context.deleteFile(note.title)
                    Toast.makeText(parent.context, String.format("%s已删除", note.title), Toast.LENGTH_SHORT).show()
                    notes.removeAt(position)
                    notifyItemRemoved(position)
                }
                setNegativeButton("Cancel") { _, _ ->}
                show()
            }

            true
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.title.text = note.title
        holder.noteFirstLine.text = note.firstLine
        holder.noteUpdateTime.text = note.createTime
    }

    override fun getItemCount() = notes.size

}