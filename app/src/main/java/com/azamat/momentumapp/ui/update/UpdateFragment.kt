package com.azamat.momentumapp.ui.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azamat.momentumapp.R
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.FragmentUpdateBinding
import com.azamat.momentumapp.ui.task.adapter.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class UpdateFragment : BottomSheetDialogFragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var firestore: FirebaseFirestore

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var updateViewModel: UpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()


        updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        binding.nameUpdate.setText(args.currentTask.title)
        binding.secondNameUpdate.setText(args.currentTask.description)

        binding.btnUserUpdate.setOnClickListener {
            updateItem(view)
        }

        binding.deleteIconUpdate.setOnClickListener {
            deleteItem()
        }

        setHasOptionsMenu(true)

    }

    private fun deleteItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Вы действительно хотите удалить ? " + args.currentTask.title)
        builder.setMessage("Вы уверены ? " + args.currentTask.title)
        builder.setPositiveButton("ДА"){ dialogInterface: DialogInterface, i: Int ->
            updateViewModel.delete(args.currentTask)
            findNavController().navigate(R.id.action_updateFragment_to_firstFragment)

            updateViewModel.deleteFirestore()
        }
        builder.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

        }

        builder.show()



    }





    private fun updateItem(view: View) {

        val updateTitle = binding.nameUpdate.text.toString()
        val updateDes = binding.secondNameUpdate.text.toString()


        val radioButtonId: Int = binding.radioGroup.checkedRadioButtonId
        val radioButton : RadioButton = view.findViewById(radioButtonId)
        val priorityId = radioButton.text.toString().toInt()

        if (checkEmpty(updateTitle, updateDes)){
            val updateTask = Task(priorityId,false, args.currentTask.id, updateTitle, updateDes)

            updateViewModel.updateTask(updateTask)

            Toast.makeText(requireContext(), "Successful update task", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_firstFragment)
        } else{
            Toast.makeText(requireContext(), "Fail update Task", Toast.LENGTH_SHORT).show()
        }

    }

    fun checkEmpty(updateTitle: String, updateDes: String): Boolean{
        return !(TextUtils.isEmpty(updateTitle) && TextUtils.isEmpty(updateDes))

    }




}