package com.tiooooo.todolist.pages.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tiooooo.todolist.databinding.ItemTodoBinding
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.pages.main.TodoAdapterListener
import com.tiooooo.todolist.util.NoteDiffCallback

class TodoAdapter(
    private val todoAdapterListener: TodoAdapterListener,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private var todoList: MutableList<Todo> = mutableListOf()

    fun setData(list: List<Todo>) {
        val diffCallback = NoteDiffCallback(this.todoList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.todoList.clear()
        this.todoList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        binding: ItemTodoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val todoBinding = binding

        fun bindItem(todo: Todo) {
            todoBinding.tvDate.text = todo.date
            todoBinding.tvMessage.text = todo.description
            todoBinding.tvId.text = "ID: ${todo.id}"
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = todoList[position]
        holder.bindItem(todoList[position])
        holder.itemView.setOnClickListener {
            todoAdapterListener.onClickDetail(item)
        }

        holder.todoBinding.btnEdit.setOnClickListener {
            todoAdapterListener.onClickEdit(item)
        }
    }
}
