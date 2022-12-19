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

class SecondActivity : BaseActivity() {

    private lateinit var binding: SecondLayoutBinding
    private var title: String? = "title"
    private var originContent: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("title")
        binding.title.text = title;

        originContent = load(title)
        originContent?.let {
            binding.content.setText(it)
            binding.content.setSelection(it.length)
        }
    }

    override fun onPause() {
        super.onPause()
        val inputText = binding.content.text.toString()
        if (inputText != originContent) {
            save(inputText)
        }
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