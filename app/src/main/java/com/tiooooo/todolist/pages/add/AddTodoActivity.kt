package com.tiooooo.todolist.pages.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.tiooooo.todolist.databinding.ActivityAddTodoBinding
import com.tiooooo.todolist.model.Todo
import com.tiooooo.todolist.pages.ViewModelFactory
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_DELETE
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_EDIT
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_ID
import com.tiooooo.todolist.pages.main.MainActivity.Companion.EXTRA_MESSAGE
import com.tiooooo.todolist.util.DateHelper

class AddTodoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val addTodoViewModel = obtainViewModel(this)

        binding.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            val isEdit = intent.getBooleanExtra(EXTRA_EDIT, false)
            val id = intent.getIntExtra(EXTRA_ID, 0)
            val message = intent.getStringExtra(EXTRA_MESSAGE).toString()

            if (isEdit) {
                supportActionBar?.title = "Edit"
                edtText.setText(message)
            }

            binding.btnDelete.isVisible = isEdit

            edtText.doAfterTextChanged {
                btnSave.isEnabled = it?.toString()?.isNotEmpty() ?: false
            }

            btnDelete.setOnClickListener {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(EXTRA_ID, id)
                    putExtra(EXTRA_MESSAGE, edtText.text.toString())
                    putExtra(EXTRA_DELETE, true)
                })

                val desc = edtText.text.toString()
                val note = Todo(
                    id = id, description = desc
                )
                addTodoViewModel.delete(note)
                finish()
            }

            btnSave.setOnClickListener {
                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(EXTRA_ID, id)
                    putExtra(EXTRA_MESSAGE, edtText.text.toString())
                    putExtra(EXTRA_EDIT, isEdit)
                })

                val desc = edtText.text.toString()
                val note = Todo(
                    id = id, description = desc, date = DateHelper.getCurrentDate()
                )
                if (isEdit) {
                    addTodoViewModel.update(note)
                } else {
                    addTodoViewModel.insert(note)
                }
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun obtainViewModel(activity: AppCompatActivity): AddTodoViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AddTodoViewModel::class.java)
    }
}
