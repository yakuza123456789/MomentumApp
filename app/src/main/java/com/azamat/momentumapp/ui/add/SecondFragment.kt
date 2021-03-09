package com.azamat.momentumapp.ui.add

import android.content.Intent.getIntent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.azamat.momentumapp.R
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.FragmentSecondBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.FirebaseFirestore


class  SecondFragment : BottomSheetDialogFragment() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var viewModel: AddViewModel
    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firestore = FirebaseFirestore.getInstance()

        viewModel = ViewModelProvider(this).get(AddViewModel::class.java)

        binding.btnUser.setOnClickListener {
            insertDataBaseToData(view)
        }
    }

    private fun insertDataBaseToData(view: View) {
        var editTitle = binding.name.text.toString()
        val editDesc = binding.secondName.text.toString()

        val radioButtonId: Int = binding.radioGroup.checkedRadioButtonId
        val radioButton : RadioButton = view.findViewById(radioButtonId)

        if (radioButton == null){
            Toast.makeText(requireContext(), "Выберите приоритет", Toast.LENGTH_SHORT).show()
        }


        val priorityId = radioButton.text.toString().toInt()

        if (checkEmpty(editTitle, editDesc)){
            val task = Task(priorityId, false, "", editTitle, editDesc)

            viewModel.addTask(task)
            Toast.makeText(requireContext(), "Add task", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_secondFragment_to_firstFragment)


        } else{
            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkEmpty(editTitle: String, editDesc: String): Boolean{
        return !(TextUtils.isEmpty(editTitle) && TextUtils.isEmpty(editDesc))

    }


}