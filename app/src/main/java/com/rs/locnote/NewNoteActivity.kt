package com.rs.locnote

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rs.locnote.databinding.ActivityNewNoteBinding
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        val title = binding.title.text.toString()
        val content = binding.content.text.toString()
        save(title, content)
    }

    private fun save(title: String, content: String) {
        try {
            val output = openFileOutput(title, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(content)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}