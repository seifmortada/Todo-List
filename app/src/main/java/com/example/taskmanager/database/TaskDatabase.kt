package com.example.taskmanager.database

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskmanager.utils.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getDao():TaskDao
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getDatabase(context: Context): TaskDatabase {
            val instance = INSTANCE
            if (instance != null) {
                return instance
            }
            val instanceNew =
                Room.databaseBuilder(context, TaskDatabase::class.java, "task_database").build()
            INSTANCE = instanceNew
            return instanceNew
        }
    }
}