package ru.shiryaev.todoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.shiryaev.todoapp.common.controllers.TodoItemController
import ru.shiryaev.todoapp.common.keys.TodoKeys
import ru.shiryaev.todoapp.databinding.ActivityMainBinding
import ru.shiryaev.todoapp.dialogs.DeleteTodoDialog
import ru.shiryaev.todoapp.models.Todo
import ru.shiryaev.todoapp.utils.IntentTags
import ru.shiryaev.todoapp.viewmodel.MainActivityViewModel
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private val todoAdapter = EasyAdapter()
    private val todoItemController = TodoItemController()
    private lateinit var todoList: ItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.progressBar.observe(this, { binding.progressBar.isVisible = it })
        mainActivityViewModel.listTodo.observe(this, { setListToAdapter(it) })

        initList()

        mainActivityViewModel.getTodoList()

        binding.fabBtn.setOnClickListener {
            val intent = Intent(this, TodoCreateActivity::class.java).apply {
                putExtra(TodoKeys.REQUEST_CODE, IntentTags.CREATE_TODO)
            }
            startActivityForResult(intent, IntentTags.CREATE_TODO)
        }

        todoItemController.onClickItemListener = { itemClick(it) }
        todoItemController.onLongClickItemListener = { itemLongClick(it) }
        todoItemController.onClickCheckTodo = { mainActivityViewModel.updateTodo(it.id!!, it.todoText!!, it.todoCheck) }
    }

    private fun initList() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }
    }

    private fun setListToAdapter(listTodo: List<Todo>?) {
        if (listTodo != null) {
            todoList = ItemList.create().apply {
                addAll(listTodo, todoItemController)
            }
            todoAdapter.setItems(todoList)
        }
        binding.infoText.isVisible = todoAdapter.itemCount == 0
    }

    private fun itemClick(todo: Todo) {
        val intent = Intent(this, TodoCreateActivity::class.java).apply {
            putExtra(TodoKeys.REQUEST_CODE, IntentTags.EDIT_TODO)
            putExtra(TodoKeys.TODO_ID, todo.id)
            putExtra(TodoKeys.TODO_TEXT, todo.todoText)
            putExtra(TodoKeys.TODO_CHECK, todo.todoCheck)
        }
        startActivityForResult(intent, IntentTags.EDIT_TODO)
    }

    private fun itemLongClick(todo: Todo) {
        DeleteTodoDialog(todo).apply {
            onCLickBtn = { todo.id?.let { it1 -> mainActivityViewModel.deleteTodo(it1) } }
        }.show(supportFragmentManager, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == IntentTags.CREATE_TODO) {
                    data.getStringExtra(TodoKeys.TODO_TEXT)?.let { mainActivityViewModel.insertTodo(it, data.getBooleanExtra(TodoKeys.TODO_CHECK, false)) }
                } else if (requestCode == IntentTags.EDIT_TODO) {
                    data.getStringExtra(TodoKeys.TODO_TEXT)?.let {
                        mainActivityViewModel.updateTodo(data.getIntExtra(TodoKeys.TODO_ID, 0),
                            it, data.getBooleanExtra(TodoKeys.TODO_CHECK, false))
                        mainActivityViewModel.getTodoList()
                    }
                }
            }
        }
    }
}