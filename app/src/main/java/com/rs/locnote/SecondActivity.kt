package com.rs.locnote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rs.locnote.databinding.SecondLayoutBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: SecondLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = SecondLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        val fruit = intent.getStringExtra("fruit")
        binding.fruitName.text = fruit
    }
}