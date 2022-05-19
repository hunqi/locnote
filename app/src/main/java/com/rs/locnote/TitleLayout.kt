package com.rs.locnote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import com.rs.locnote.databinding.TitleBinding

class TitleLayout(context: Context, attrs : AttributeSet) : LinearLayout(context, attrs) {

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.title, this)
        val binding = TitleBinding.bind(view)
        binding.titleBack.setOnClickListener {
            val activity = context as Activity
            activity.finish()
        }
        binding.titleEdit.setOnClickListener {
            val intent = Intent(context, NewNoteActivity::class.java)
            startActivity(context, intent, null)
        }
    }

}