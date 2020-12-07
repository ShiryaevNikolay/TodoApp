package ru.shiryaev.todoapp.database.repository

import android.app.Application
import ru.shiryaev.todoapp.database.TodoDBManager
import ru.shiryaev.todoapp.models.Todo

class AppRepository(application: Application) {
    private var todoDBManager = TodoDBManager(application)

    fun insert(textTodo: String, checkTodo: Boolean) {
        todoDBManager.openDB()
        todoDBManager.insertToDB(textTodo, checkTodo)
        todoDBManager.closeDB()
    }

    fun update(_id: Int, textTodo: String, checkTodo: Boolean) {
        todoDBManager.openDB()
        todoDBManager.updateTodo(_id, textTodo, checkTodo)
        todoDBManager.closeDB()
    }

    fun delete(_id: Int) {
        todoDBManager.openDB()
        todoDBManager.deleteTodo(_id)
        todoDBManager.closeDB()
    }

    fun getAllTodo() : List<Todo> {
        todoDBManager.openDB()
        val listTodo = todoDBManager.getFromDB()
        todoDBManager.closeDB()
        return listTodo
    }
}