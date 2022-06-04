package com.rs.locnote

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rs.locnote.databinding.FirstLayoutBinding

class FirstActivity : BaseActivity() {

    private lateinit var binding: FirstLayoutBinding
    private val notes = ArrayList<Note>()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNotes()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        adapter = NoteAdapter(notes)
        adapter.notifyDataSetChanged()
        binding.recyclerView.adapter = adapter

        binding.newNote.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }

    private fun initNotes() {
        val db = dbHelper.readableDatabase
        val cursor = db.query("Note", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                createNote(cursor)?.let { notes.add(it) }
            }while (cursor.moveToNext())
        }
        cursor.close()
    }

    override fun onRestart() {
        super.onRestart()
        notes.clear()
        initNotes()
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("Range")
    private fun createNote(cursor: Cursor?): Note? {
        val title = cursor?.getString(cursor.getColumnIndex("title"))
        val firstLine = cursor?.getString(cursor.getColumnIndex("content"))
        val createTime = cursor?.getString(cursor.getColumnIndex("update_time"))
        return title?.let { Note(it, firstLine, createTime) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_item -> Toast.makeText(this, "You clicked Add", Toast.LENGTH_SHORT).show()
            R.id.remove_item -> Toast.makeText(this, "You clicked Remove", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}