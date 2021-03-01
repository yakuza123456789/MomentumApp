package com.azamat.momentumapp.ui.update

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.data.repository.Repository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateViewModel(application: Application) : AndroidViewModel(application) {


    private var db = Firebase.firestore

    fun updateTask(task: Task){
        db.collection("users").document("qDLgbBsD822k1Wj0Pl4w").collection("tasks").document(task.id.toString()).set(
            task
        )
    }

    fun delete(task: Task){
        viewModelScope.launch(Dispatchers.IO){
            db.collection("users").document("qDLgbBsD822k1Wj0Pl4w").collection("tasks")
                .document(task.id.toString()).delete()
        }
    }



}