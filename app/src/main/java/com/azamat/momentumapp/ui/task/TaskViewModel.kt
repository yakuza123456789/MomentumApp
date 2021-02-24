package com.azamat.momentumapp.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.data.local.TaskDataBase
import com.azamat.momentumapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTask: LiveData<List<Task>>
    val repository: Repository

    init {
        val userDao = TaskDataBase.getTaskDataBase(application).taskDao()
        repository = Repository(userDao)
        readAllTask = repository.readAllTask
    }

    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(task)
        }
    }

    fun deleteAllTask(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllTask()
        }
    }
}