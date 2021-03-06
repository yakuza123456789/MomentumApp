package com.azamat.momentumapp.ui.add

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

class AddViewModel(application: Application): AndroidViewModel(application) {

    private var db = Firebase.firestore


    fun addTask(task: Task){
        db.collection("users").document("qDLgbBsD822k1Wj0Pl4w")
            .collection("tasks")
            .add(task)
            .addOnSuccessListener {
                Log.d("fire", "addTaskFirestore: success " + it)
            }
            .addOnFailureListener {
                Log.d("fire", "addTaskFirestore: fail " + it)
            }
    }
}