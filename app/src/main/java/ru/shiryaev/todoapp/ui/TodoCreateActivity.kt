package ru.shiryaev.todoapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import ru.shiryaev.todoapp.common.keys.TodoKeys
import ru.shiryaev.todoapp.databinding.ActivityTodoCreateBinding
import ru.shiryaev.todoapp.utils.IntentTags

class TodoCreateActivity : AppCompatActivity(), TextWatcher {

    private lateinit var binding: ActivityTodoCreateBinding
    private var textTodo = ""
    private var checkTodo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoCreateBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setNavigationOnClickListener { finish() }

        if (intent.extras?.getInt(TodoKeys.REQUEST_CODE, 0) == IntentTags.EDIT_TODO) {
            textTodo = intent.getStringExtra(TodoKeys.TODO_TEXT).toString()
            binding.inputTodo.setText(textTodo)
            checkTodo = intent.getBooleanExtra(TodoKeys.TODO_CHECK, false)
        }

        binding.inputTodo.addTextChangedListener(this)

        binding.fab.setOnClickListener { createTodo() }
    }

    private fun createTodo() {
        if (textTodo != "") {
            val data = Intent().apply {
                if (intent.extras?.getInt(TodoKeys.REQUEST_CODE, 0) == IntentTags.EDIT_TODO) putExtra(TodoKeys.TODO_ID, intent.getIntExtra(TodoKeys.TODO_ID, 0))
                putExtra(TodoKeys.TODO_TEXT, textTodo)
                putExtra(TodoKeys.TODO_CHECK, checkTodo)
            }
            setResult(RESULT_OK, data)
            finish()
        } else {
            finish()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        TODO("Not yet implemented")
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        TODO("Not yet implemented")
    }

    override fun afterTextChanged(s: Editable?) {
        textTodo = s.toString()
    }
}