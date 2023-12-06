package com.example.taskmanager.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.taskmanager.database.TaskDatabase
import com.example.taskmanager.repository.TaskRepository
import com.example.taskmanager.utils.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    private val database: TaskDatabase
    val readAllData: LiveData<List<Task>>

    private val _tasks: MutableLiveData<List<Task>> = MutableLiveData(emptyList())
    val tasks: LiveData<List<Task>> = _tasks


    init {
        database = TaskDatabase.getDatabase(application.applicationContext)
        repository = TaskRepository(database.getDao())
        readAllData = repository.getAllTasks
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
            _tasks.postValue(emptyList())
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
            
        }
    }
}