package com.azamat.momentumapp.ui.task

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.data.repository.Repository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashMap
import kotlin.reflect.KProperty

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllTask: MutableLiveData<List<Task>> = MutableLiveData()
    private var db = Firebase.firestore

    init {
        db.collection("users").document("qDLgbBsD822k1Wj0Pl4w").collection("tasks")
            .addSnapshotListener { value, error ->
                var list = ArrayList<Task>()
                if (list.size > 0) {
                    list.clear()
                }
                for (index in value!!.documents.withIndex()) {
                    var doc = value.documents[index.index]
                    val id = index.value.id
                    val data = doc.toObject(Task::class.java)
                    data?.id = id
                    list.add(data!!)
                }
                readAllTask.value = list
            }
    }

    fun delete(task: Task) {
        db.collection("users").document("qDLgbBsD822k1Wj0Pl4w").collection("tasks")
            .document(task.id.toString()).delete()
    }

    fun deleteAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
//            repository.deleteAllTask()
        }
    }
}