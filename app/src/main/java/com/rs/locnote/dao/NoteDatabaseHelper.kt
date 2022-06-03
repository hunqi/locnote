package com.rs.locnote.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class NoteDatabaseHelper(val context: Context, name:String, version:Int):
    SQLiteOpenHelper(context, name, null, version) {

    private val createNote = "create table Note(" +
            "id integer primary key autoincrement," +
            "title text," +
            "content text," +
            "del_flag integer," +
            "create_time text," +
            "update_time text)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(createNote)
        Toast.makeText(context, "Table Note created", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}