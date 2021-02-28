package com.azamat.momentumapp.ui.task

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.azamat.momentumapp.R
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.FragmentFirstBinding
import com.azamat.momentumapp.ui.task.adapter.TaskAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.reflect.Field
import java.util.*
import kotlin.collections.ArrayList


class FirstFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentFirstBinding
    private lateinit var taskViewModel: TaskViewModel

//    private val KEY_TITLE: String = "title"
//    private val KEY_DESC: String = "description"
//
//    private var documFire: DocumentReference = firestore.document("task")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        firestore = FirebaseFirestore.getInstance()


        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        binding.btnFloat.setOnClickListener {
        Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment)
        }

        val adapter = TaskAdapter()
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        deleteToSwipe(adapter, recyclerView)

        taskViewModel.readAllTask.observe(viewLifecycleOwner, Observer {
            adapter.setData(it as ArrayList<Task>)
        })

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete){
//            deleteToAll()
        }
        return super.onOptionsItemSelected(item)
    }



    private fun deleteToSwipe(adapter: TaskAdapter, recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                taskDeleteFireStore(adapter.taskList[viewHolder.adapterPosition])
                taskViewModel.delete(adapter.deleteItem(viewHolder.adapterPosition))
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recyclerView)


    }

    private fun taskDeleteFireStore(task: Task) {

        firestore.collection("tasks").document("qDLgbBsD822k1Wj0Pl4w").collection(
            "tasks"
        ).document()
            .delete()
            .addOnSuccessListener {
                Log.d("fire", "taskDeleteFireStore: Success")
            }
            .addOnFailureListener {
                Log.d("fire", "taskDeleteFireStore: Fail")
            }

    }

}