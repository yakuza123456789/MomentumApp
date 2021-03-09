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

import com.azamat.momentumapp.ui.task.adapter.FinishTaskAdapter
import com.azamat.momentumapp.ui.task.adapter.TaskAdapter

import com.google.firebase.firestore.FirebaseFirestore

import kotlin.collections.ArrayList
class FirstFragment : Fragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: FragmentFirstBinding
    private lateinit var taskViewModel: TaskViewModel

//    private val taskAdapter = TaskAdapter(requireContext(), this)
//    private val finishTaskAdapter = FinishTaskAdapter()
//
//
//
//    override fun onClickCheckBox(position: Task) {
//        position.isChecked = false
//        taskAdapter.deleteItem(position)
//        finishTaskAdapter.finishItemTask(taskAdapter.getCompletedTask() as ArrayList<Task>)
//    }
//
//    override fun onClickCheckBox(position: Int) {
//
//    }

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

        val recyclerView = binding.recyclerView
        val finishRv = binding.finishRecyclerView


        val adapter = TaskAdapter(requireContext(), object : TaskAdapter.CheckBoxListener {
            override fun onClickCheckBox(position: Int) {
                recyclerView.visibility = View.INVISIBLE
                finishRv.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "successful task $position", Toast.LENGTH_SHORT).show()
            }

//            override fun onClickCheckBox(task: Task) {
//                task.isChecked = false
//            }

        })

        recyclerView.adapter = adapter



        val finishAdapter = FinishTaskAdapter()

        finishRv.adapter = finishAdapter

//        itemTouchHelperr(finishAdapter, finishRv)

        deleteToSwipe(finishAdapter, finishRv)

        taskViewModel.readAllTask.observe(viewLifecycleOwner, Observer {
            adapter.setData(it as ArrayList<Task>)
            Log.d("adapter ", "onViewCreated: show all")
        })

        taskViewModel.readAllTask.observe(viewLifecycleOwner, Observer {
            finishAdapter.finishItemTask(it as ArrayList<Task>)
        })



    }

//    private fun itemTouchHelperr(finishAdapter: FinishTaskAdapter, finishRv: RecyclerView) {
//        var itemTouchHelper = ItemTouchHelper(DeleteFinishTask(finishAdapter))
//        itemTouchHelper.attachToRecyclerView(finishRv)
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete){
        }

        return super.onOptionsItemSelected(item)
    }


    private fun deleteToSwipe(adapter: FinishTaskAdapter, finishRv: RecyclerView ) {
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
                taskViewModel.delete(adapter.deleteToSwipe(viewHolder.adapterPosition))
                Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show()


            }

        }).attachToRecyclerView(finishRv)
    }
}


