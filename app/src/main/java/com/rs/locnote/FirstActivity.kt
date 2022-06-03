package com.rs.locnote

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rs.locnote.dao.NoteDatabaseHelper
import com.rs.locnote.databinding.FirstLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Stream
import kotlin.collections.ArrayList

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: FirstLayoutBinding
    private val notes = ArrayList<Note>()
    private lateinit var dbHelper: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = NoteDatabaseHelper(this, "locnote.db", 1)
        dbHelper.writableDatabase

        initNotes()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = NoteAdapter(notes)
        binding.recyclerView.adapter = adapter

        binding.newNote.setOnClickListener {
            val intent = Intent(this, NewNoteActivity::class.java)
            ContextCompat.startActivity(this, intent, null)
        }
    }

    private fun initNotes() {
        val db = dbHelper.writableDatabase
        val cursor = db.query("Note", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                createNote(cursor)?.let { notes.add(it) }
            }while (cursor.moveToNext())
        }
        cursor.close()
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