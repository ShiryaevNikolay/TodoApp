package ru.shiryaev.todoapp.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import ru.shiryaev.todoapp.R
import ru.shiryaev.todoapp.databinding.DialogTodoMenuBinding
import ru.shiryaev.todoapp.models.Todo

class DeleteTodoDialog(private val todo: Todo) : DialogFragment() {

    var onCLickBtn: ((Todo) -> Unit)? = null
    private var _binding: DialogTodoMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTodoMenuBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_bg)
        binding.deleteBtn.setOnClickListener {
            onCLickBtn?.invoke(todo)
            dismiss()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}