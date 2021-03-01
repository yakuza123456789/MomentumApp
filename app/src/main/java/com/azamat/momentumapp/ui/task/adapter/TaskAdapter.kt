package com.azamat.momentumapp.ui.task.adapter


import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
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
    if (isChecked){
        titleText.paintFlags = titleText.paintFlags or STRIKE_THRU_TEXT_FLAG
        descText.paintFlags = descText.paintFlags or STRIKE_THRU_TEXT_FLAG
        titleText.setTextColor(Color.DKGRAY)
        descText.setTextColor(Color.DKGRAY)
    } else {
        titleText.paintFlags = titleText.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        descText.paintFlags = descText.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        titleText.setTextColor(Color.WHITE)
        descText.setTextColor(Color.WHITE)
    }

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        val modelTask = taskList[position]
        holder.binding.titleItem.text = modelTask.title

        holder.binding.descItem.text = modelTask.description

        holder.binding.checkBox.isChecked = modelTask.isChecked


        toggleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, modelTask.isChecked)
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChacked ->
            toggleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, isChacked)
            modelTask.isChecked = !!modelTask.isChecked
        }

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