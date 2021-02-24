package com.azamat.momentumapp.ui.update

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.data.local.TaskDataBase
import com.azamat.momentumapp.data.repository.Repository
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {


    private var db = Firebase.firestore

    val readAllTask: LiveData<List<Task>>
    val repository: Repository

    init {
        val taskDao = TaskDataBase.getTaskDataBase(application).taskDao()
        repository = Repository(taskDao)
        readAllTask = repository.readAllTask
    }


    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateTask(task)
        }
    }

    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteTask(task)
        }
    }

     fun deleteFirestore() {

        db.collection("users").document("qDLgbBsD822k1Wj0Pl4w")
            .collection("tasks").document()
            .delete()
            .addOnSuccessListener {
                Log.d("fire", "deleteFirestore: onSuccess")
            }
            .addOnFailureListener {
                Log.d("fire", "deleteFirestore: onFailure")
            }

    }


}