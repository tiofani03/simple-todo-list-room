package com.tiooooo.todolist.pages.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiooooo.todolist.data.local.TodoRepository
import com.tiooooo.todolist.model.Todo

class MainViewModel(application: Application) : ViewModel() {

    private val mNoteRepository: TodoRepository = TodoRepository(application)
    fun getAllTodos(): LiveData<List<Todo>> = mNoteRepository.getAllTodos()
}
