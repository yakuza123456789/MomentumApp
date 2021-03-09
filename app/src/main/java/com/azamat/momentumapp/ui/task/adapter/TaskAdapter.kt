package com.azamat.momentumapp.ui.task.adapter


import android.content.Context
import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.azamat.momentumapp.R
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.ItemTaskBinding
import com.azamat.momentumapp.ui.task.FirstFragmentDirections
import org.w3c.dom.Text

public class TaskAdapter(val context: Context, var listenr: CheckBoxListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var taskList = ArrayList<Task>()

    interface CheckBoxListener {
        fun onClickCheckBox(position: Int)
//        fun onClickCheckBox(position: Task)
    }

    init {
        this.listenr = listenr
    }

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun onBind(task: Task) {
//            binding.checkBox.setOnClickListener { listenr.onClickCheckBox(task) }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    private fun toggleStrikeTrough(titleText: TextView, descText: TextView, isChecked: Boolean) {
        if (isChecked) {
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
//        holder.onBind(taskList[position])

        val modelTask = taskList[position]
        holder.binding.titleItem.text = modelTask.title

        holder.binding.descItem.text = modelTask.description

        holder.binding.checkBox.isChecked = modelTask.isChecked

        toggleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, modelTask.isChecked)
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChacked ->
            toggleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, isChacked)

            holder.binding.itemLayout.setBackgroundResource(R.color.completeTask)

            modelTask.isChecked = !!modelTask.isChecked
        }


        holder.binding.itemLayout.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToUpdateFragment(modelTask)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.checkBox.setOnClickListener {
            listenr.onClickCheckBox(position)

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

    fun setData(task: ArrayList<Task>) {
        this.taskList = task
        notifyDataSetChanged()
    }

    fun deleteItem(pos: Int): Task {
        return taskList.get(pos)
    }

//    fun deleteItem(pos: Task) {
//        taskList.remove(pos)
//        notifyDataSetChanged()
//    }
//
//    fun getCompletedTask(): List<Task> {
//        return taskList.filter { !it.isChecked }
//    }
}