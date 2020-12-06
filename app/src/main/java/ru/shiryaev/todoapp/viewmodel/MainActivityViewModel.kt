package ru.shiryaev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.shiryaev.todoapp.database.repository.AppRepository
import ru.shiryaev.todoapp.models.Todo

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private var repository = AppRepository(application)
    var listTodo = MutableLiveData<List<Todo>>()
        private set
    var progressBar = MutableLiveData<Boolean>()

    fun getTodoList() {
        progressBar.value = true
        listTodo.value = repository.getAllTodo()
        progressBar.value = false
    }

    fun insertTodo(textTodo: String, checkTodo: Boolean) {
        progressBar.value = true
        repository.insert(textTodo, checkTodo)
        progressBar.value = false
        getTodoList()
    }

    fun updateTodo(_id: Int, textTodo: String, checkTodo: Boolean) {
        progressBar.value = true
        repository.update(_id, textTodo, checkTodo)
        progressBar.value = false
    }

    fun deleteTodo(_id: Int) {
        progressBar.value = true
        repository.delete(_id)
        progressBar.value = false
        getTodoList()
    }
}