package ru.shiryaev.todoapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDBHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "todo_db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_TODO ($_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_TEXT TEXT, $KEY_CHECK INTEGER DEFAULT 0)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }
//
//    fun updateTodo(id: Long, values: ContentValues) {
//        val db: SQLiteDatabase = this.writableDatabase
//        db.update(TABLE_TODO, values, "$_ID = $id", null)
//    }

    companion object {
        const val TABLE_TODO = "todo_table"
        const val _ID = "_id"
        const val KEY_TEXT = "text_todo"
        const val KEY_CHECK = "check_todo"
    }
}