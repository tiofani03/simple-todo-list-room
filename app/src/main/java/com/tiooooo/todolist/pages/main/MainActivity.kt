package com.tiooooo.todolist.pages.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiooooo.todolist.databinding.ActivityMainBinding
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.pages.ViewModelFactory
import com.tiooooo.todolist.pages.add.AddTodoActivity
import com.tiooooo.todolist.pages.detail.DetailActivity
import com.tiooooo.todolist.pages.main.adapter.TodoAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_EDIT = "extra_edit"
        const val EXTRA_DELETE = "extra_delete"
    }

    private lateinit var binding: ActivityMainBinding
    private val todoAdapter = TodoAdapter(handleAdapterListener())
    private lateinit var mainViewModel: MainViewModel

    private val editLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val isEdit = result.data?.getBooleanExtra(EXTRA_EDIT, false) ?: false
                val text = result.data?.getStringExtra(EXTRA_MESSAGE) ?: ""

                val showMessage = if (isEdit) "$text Berhasil dihapus"
                else "$text berhasil diedit"

                Toast.makeText(this, showMessage, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.getAllTodos().observe(this) {
            binding.tvEmptyMessage.isVisible = it.isEmpty()
            todoAdapter.setData(it)
        }

        binding.rvTodo.apply {
            adapter = todoAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }

        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this, AddTodoActivity::class.java))
        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private fun handleAdapterListener() = object : TodoAdapterListener {
        override fun onClickDetail(todo: Todo) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(EXTRA_ID, todo.id)
            intent.putExtra(EXTRA_MESSAGE, todo.description)

            startActivity(intent)
        }

        override fun onClickEdit(todo: Todo) {
            val intent = Intent(this@MainActivity, AddTodoActivity::class.java)
            intent.putExtra(EXTRA_ID, todo.id)
            intent.putExtra(EXTRA_EDIT, true)
            intent.putExtra(EXTRA_MESSAGE, todo.description)

            editLauncher.launch(intent)
        }

    }
}


interface TodoAdapterListener {
    fun onClickDetail(todo: Todo)
    fun onClickEdit(todo: Todo)
}
