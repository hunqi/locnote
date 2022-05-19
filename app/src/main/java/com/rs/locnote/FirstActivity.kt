package com.rs.locnote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.rs.locnote.databinding.FirstLayoutBinding
import java.util.*
import java.util.stream.Stream
import kotlin.collections.ArrayList

class FirstActivity : AppCompatActivity() {

    private lateinit var binding: FirstLayoutBinding
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FirstLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileList())
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, pos, _ ->
            val fruit = fileList()[pos]
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title", fruit)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, fileList())
        binding.listView.adapter = adapter
        binding.listView.setOnItemClickListener { _, _, pos, _ ->
            val fruit = fileList()[pos]
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("title", fruit)
            startActivity(intent)
        }
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