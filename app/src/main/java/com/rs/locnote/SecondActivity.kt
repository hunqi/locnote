package com.rs.locnote

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rs.locnote.databinding.SecondLayoutBinding
import java.io.*
import java.lang.StringBuilder

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondLayoutBinding
    private var title: String? = "title"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        title = intent.getStringExtra("title")
        binding.title.text = title;

        val content = load(title)
        if (content.isNotEmpty()) {
            binding.content.setText(content)
            binding.content.setSelection(content.length)
            Toast.makeText(this, "Restoring succeeded", Toast.LENGTH_SHORT).show()
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
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}