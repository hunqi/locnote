package com.rs.locnote

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import com.rs.locnote.databinding.ActivityNewNoteBinding
import java.io.BufferedWriter
import java.io.IOException
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.*

class NewNoteActivity : BaseActivity() {
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
        val db = dbHelper.writableDatabase
        try {
            val output = openFileOutput(title, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(content)
            }

            db.insert("Note", null, noteValues(title, content))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun noteValues(title: String, content: String): ContentValues? {
        return ContentValues().apply {
            put("title", title)
            put("content", firstLine(content))
            put("del_flag", 0)
            put("create_time", nowTimeString())
            put("update_time", nowTimeString())
        }
    }

    private fun nowTimeString(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
    }

    private fun firstLine(content: String): String? {
        val lines = content?.split("\n");
        return if (lines != null && lines.isNotEmpty()) {
            lines[0]
        } else {
            ""
        }
    }
}