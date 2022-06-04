package com.rs.locnote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rs.locnote.dao.NoteDatabaseHelper

open class BaseActivity: AppCompatActivity() {

    lateinit var dbHelper: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = NoteDatabaseHelper(this, "locnote.db", 1)
        dbHelper.writableDatabase
    }

}