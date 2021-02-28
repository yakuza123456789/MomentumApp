package com.azamat.momentumapp.data.repository

import androidx.lifecycle.LiveData
import com.azamat.momentumapp.data.local.Task

class Repository() {

    suspend fun addTask(task: Task){
//        taskDao.addTask(task)
    }

    suspend fun updateTask(task: Task){
//        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task){
//        taskDao.deleteTask(task)
    }

    suspend fun deleteAllTask(){
//        taskDao.deleteAllTask()
    }

}