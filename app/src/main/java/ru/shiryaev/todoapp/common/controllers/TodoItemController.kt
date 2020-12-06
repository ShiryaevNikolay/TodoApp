package ru.shiryaev.todoapp.common.controllers

import android.graphics.Paint
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import ru.shiryaev.todoapp.R
import ru.shiryaev.todoapp.models.Todo
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class TodoItemController : BindableItemController<Todo, TodoItemController.Holder>() {

    var onClickItemListener: ((Todo) -> Unit)? = null
    var onLongClickItemListener: ((Todo) -> Unit)? = null
    var onClickCheckTodo: ((Todo) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Todo) = data.id.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Todo>(parent, R.layout.todo_item) {
        private val textTodo = itemView.findViewById<TextView>(R.id.todo_tv)
        private val checkTodo = itemView.findViewById<CheckBox>(R.id.todo_cb)

        override fun bind(data: Todo?) {
            if (data != null) {
                textTodo.text = data.todoText
                checkTodo.isChecked = data.todoCheck
                if (checkTodo.isChecked) {
                    textTodo.paintFlags = textTodo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }

                checkTodo.setOnCheckedChangeListener { _, isChecked ->
                    data.todoCheck = isChecked
                    if (isChecked) {
                        textTodo.paintFlags = textTodo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        textTodo.paintFlags = 0
                    }
                    onClickCheckTodo?.invoke(data)
                }

                itemView.setOnClickListener { onClickItemListener?.invoke(data) }
                itemView.setOnLongClickListener {
                    onLongClickItemListener?.invoke(data)
                    true
                }
            }
        }
    }
}