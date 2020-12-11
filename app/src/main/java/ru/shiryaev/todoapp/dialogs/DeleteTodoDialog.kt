package ru.shiryaev.todoapp.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
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
        binding.deleteBtn.setOnClickListener {
            onCLickBtn?.invoke(todo)
            dismiss()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.apply {
            attributes = p
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}