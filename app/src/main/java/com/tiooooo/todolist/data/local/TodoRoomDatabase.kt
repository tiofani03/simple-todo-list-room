package com.tiooooo.todolist.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tiooooo.todolist.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class TodoRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): TodoDao

    companion object {
        @Volatile
        private var INSTANCE: TodoRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TodoRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TodoRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, TodoRoomDatabase::class.java, "note_database"
                    ).build()
                }
            }
            return INSTANCE as TodoRoomDatabase
        }
    }
}
