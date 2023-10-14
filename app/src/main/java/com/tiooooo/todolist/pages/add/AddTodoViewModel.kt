package com.tiooooo.todolist.pages.add

import android.app.Application
import androidx.lifecycle.ViewModel
import com.tiooooo.todolist.data.local.TodoRepository
import com.tiooooo.todolist.model.Todo

class AddTodoViewModel(application: Application) : ViewModel() {
    private val mNoteRepository: TodoRepository = TodoRepository(application)
    fun insert(todo: Todo) {
        mNoteRepository.insert(todo)
    }

    fun update(todo: Todo) {
        mNoteRepository.update(todo)
    }

    fun delete(note: Todo) {
        mNoteRepository.delete(note)
    }
}
