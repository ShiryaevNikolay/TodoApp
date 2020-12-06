package ru.shiryaev.todoapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.shiryaev.todoapp.common.keys.TodoKeys
import ru.shiryaev.todoapp.models.Todo

class TodoDBManager(
    context: Context
) {
    private val todoDBHelper = TodoDBHelper(context)
    private lateinit var db: SQLiteDatabase

    fun openDB() {
        db = todoDBHelper.writableDatabase
    }

    fun closeDB() {
        todoDBHelper.close()
    }

    fun insertToDB(textTodo: String, checkTodo: Boolean) {
        val values = ContentValues().apply {
            put(TodoDBHelper.KEY_TEXT, textTodo)
            put(TodoDBHelper.KEY_CHECK, checkTodo)
        }
        db.insert(TodoDBHelper.TABLE_TODO, null, values)
    }

    fun updateTodo(_id: Int, textTodo: String, checkTodo: Boolean) {
        val values = ContentValues().apply {
            put(TodoDBHelper.KEY_TEXT, textTodo)
            put(TodoDBHelper.KEY_CHECK, checkTodo)
        }
        db.update(TodoDBHelper.TABLE_TODO, values, "${TodoDBHelper._ID} = $_id", null)
    }

    fun updateTodoList(listTodo: List<Todo>) {
        for (todo in listTodo) {
            updateTodo(todo.id!!, todo.todoText!!, todo.todoCheck)
        }
    }

    fun deleteTodo(_id: Int) {
        db.delete(TodoDBHelper.TABLE_TODO, "${TodoDBHelper._ID} = $_id", null)
    }

    fun getFromDB() : List<Todo> {
        val listTodo: MutableList<Todo> = ArrayList()
        val cursor: Cursor = db.query(TodoDBHelper.TABLE_TODO, null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val todo = Todo().apply {
                id = cursor.getInt(cursor.getColumnIndex(TodoDBHelper._ID))
                todoText = cursor.getString(cursor.getColumnIndex(TodoDBHelper.KEY_TEXT))
                todoCheck = cursor.getInt(cursor.getColumnIndex(TodoDBHelper.KEY_CHECK)) == 1
            }
            listTodo.add(todo)
        }
        cursor.close()
        return listTodo
    }
}