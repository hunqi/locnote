package com.rs.locnote

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val notes: List<Note>): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.noteTitle)
        val noteFirstLine: TextView = view.findViewById(R.id.noteFirstLine)
        val noteUpdateTime: TextView = view.findViewById(R.id.noteUpdateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        val viewHolder = ViewHolder(view)
        val intent = Intent(parent.context, SecondActivity::class.java)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.absoluteAdapterPosition
            val note = notes[position]
            intent.putExtra("title", note.title)
            startActivity(parent.context, intent, null)
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