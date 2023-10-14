package com.tiooooo.todolist.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.tiooooo.todolist.model.Todo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TodoRepository(application: Application) {
    private val mNotesDao: TodoDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = TodoRoomDatabase.getDatabase(application)
        mNotesDao = db.noteDao()
    }

    fun getAllTodos(): LiveData<List<Todo>> = mNotesDao.getAllTodos()
    fun insert(note: Todo) {
        executorService.execute { mNotesDao.insert(note) }
    }

    fun delete(note: Todo) {
        executorService.execute { mNotesDao.delete(note) }
    }

    fun update(note: Todo) {
        executorService.execute { mNotesDao.update(note) }
    }
}
