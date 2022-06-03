package com.rs.locnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rs.locnote.databinding.FirstLayoutBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Stream
import kotlin.collections.ArrayList

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: FirstLayoutBinding
    private val notes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNotes()
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val adapter = NoteAdapter(notes)
        binding.recyclerView.adapter = adapter
    }

    private fun initNotes() {
        // todo need to load data from database
        val note1 = Note("test1", "first line1", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
        val note2 = Note("test2", "first line2", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))

        notes.add(note2)
        notes.add(note1)
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