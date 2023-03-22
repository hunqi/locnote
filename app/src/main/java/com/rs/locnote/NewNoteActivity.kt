package com.rs.locnote

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
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

    override fun onPause() {
        super.onPause()
        val content = binding.content.text.toString()
        val title = getTitle(content)
        save(title, content)
    }

    private fun getTitle(content: String): String {
        var title = binding.title.text.toString()
        if (title.isBlank() && content.isNotBlank()) {
            title = if (content.length > 10) {
                content.substring(0, 10)
            } else {
                content
            }
        }
        return title
    }

    private fun save(title: String, content: String) {
        Log.i(javaClass.simpleName, "save: title=$title")

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
        } finally {
            db?.close()
            Log.i(javaClass.simpleName, "save() done")
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