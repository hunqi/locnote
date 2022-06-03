package com.rs.locnote

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rs.locnote.dao.NoteDatabaseHelper
import com.rs.locnote.databinding.SecondLayoutBinding
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondLayoutBinding
    private var title: String? = "title"
    private lateinit var dbHelper: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        dbHelper = NoteDatabaseHelper(this, "Note.db", 1)

        title = intent.getStringExtra("title")
        binding.title.text = title;

        val content = load(title)
        if (content.isNotEmpty()) {
            binding.content.setText(content)
            binding.content.setSelection(content.length)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val inputText = binding.content.text.toString()
        save(inputText)
    }

    private fun load(title: String?): String {
        val content = StringBuilder()
        try {
            val input = openFileInput(title)
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it).append("\n")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }

    private fun save(content: String) {
        try {
            val output = openFileOutput(title, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(content)
            }

            // update
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("content", firstLine(content))
            values.put("update_time", nowTimeString())
            db.update("Note", values, "title = ?", arrayOf(title))
        } catch (e: IOException) {
            e.printStackTrace()
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