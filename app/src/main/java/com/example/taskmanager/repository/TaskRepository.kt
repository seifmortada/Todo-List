package com.example.taskmanager.repository

import androidx.lifecycle.LiveData
import com.example.taskmanager.database.TaskDao
import com.example.taskmanager.utils.Task

class TaskRepository(private val dao:TaskDao) {
  val getAllTasks :LiveData<List<Task>> = dao.getAllTasks()

    suspend fun addTask(task: Task){
        dao.addTask(task)
    }
    suspend fun deleteTask(task: Task){
        dao.deleteTask(task)
    }
    suspend fun deleteAllTasks(){
        dao.deleteAllTasks()
    }
    suspend fun updateTask(task: Task){
        dao.updateTask(task)
    }
}