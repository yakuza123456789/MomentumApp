package com.azamat.momentumapp.ui.task.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.azamat.momentumapp.R
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.ItemTaskBinding
import com.azamat.momentumapp.ui.task.FirstFragmentDirections
import org.w3c.dom.Text

public class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList = ArrayList<Task>()
    private var lastChackedPosition: Int = 0

    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    private fun toggleStrikeTrough(titleText: TextView, descText: TextView, isChecked: Boolean){


    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val modelTask = taskList[position]
        holder.binding.titleItem.text = modelTask.title

        holder.binding.descItem.text = modelTask.description

        holder.binding.checkBox.isChecked = modelTask.isChecked



//        val colorId: Int
//        val priority: Int = modelTask.priority
//        when (priority){
//            1 ->
//               colorId = holder.itemView.resources.getColor(android.R.color.holo_red_light)
//            2 ->
//               colorId = holder.itemView.resources.getColor(android.R.color.holo_orange_light)
//            else -> {
//                colorId = holder.itemView.resources.getColor(android.R.color.holo_green_light)
//            }
//        }

//        holder.binding.titleItem.setBackgroundColor(colorId)


//        holder.binding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
//            if (b){
//                taskList!![lastChackedPosition].isChecked = false
//                lastChackedPosition = position
//            }
//            taskList!![position].isChecked = b
//            notifyDataSetChanged()
//        }


        holder.binding.itemLayout.setOnClickListener {
        val action = FirstFragmentDirections.actionFirstFragmentToUpdateFragment(modelTask)
            holder.itemView.findNavController().navigate(action)
        }

        val colorId: Int
        val priority: Int = modelTask.priorityId!!
        colorId = when (priority) {
            1 -> holder.itemView.getResources().getColor(R.color.btnMedium)
            2 -> holder.itemView.getResources().getColor(R.color.btnHigh)
            else -> holder.itemView.getResources().getColor(R.color.btnHighest)
        }
        holder.binding.cardViewColor.setBackgroundColor(colorId)
    }

    fun setData(task: ArrayList<Task>){
            this.taskList = task
        notifyDataSetChanged()
    }

   fun deleteItem(pos: Int): Task {
       return taskList.get(pos)
   }
}