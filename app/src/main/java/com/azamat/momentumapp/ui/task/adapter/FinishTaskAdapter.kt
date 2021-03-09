package com.azamat.momentumapp.ui.task.adapter

import android.graphics.Color
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.azamat.momentumapp.data.local.Task
import com.azamat.momentumapp.databinding.ItemFinishTaskBinding

class FinishTaskAdapter: RecyclerView.Adapter<FinishTaskAdapter.FinishViewHolder>() {

    var taskList = ArrayList<Task>()

    class FinishViewHolder(var binding: ItemFinishTaskBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishViewHolder {
        val binding = ItemFinishTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinishViewHolder(binding)
    }

    private fun toglleStrikeTrough(titleItem: TextView, descItem: TextView, checked: Boolean) {
        if(!checked)
            titleItem.paintFlags = titleItem.paintFlags or STRIKE_THRU_TEXT_FLAG
            descItem.paintFlags = descItem.paintFlags or STRIKE_THRU_TEXT_FLAG
            titleItem.setTextColor(Color.DKGRAY)
            descItem.setTextColor(Color.DKGRAY)

    }

    override fun onBindViewHolder(holder: FinishViewHolder, position: Int) {
        val modelTask = taskList[position]
        holder.binding.titleItem.text = modelTask.title
        holder.binding.descItem.text = modelTask.description
        holder.binding.checkBox.isChecked = modelTask.isChecked

        toglleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, modelTask.isChecked)
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            toglleStrikeTrough(holder.binding.titleItem, holder.binding.descItem, isChecked)

            modelTask.isChecked = !modelTask.isChecked

    }

}

    override fun getItemCount(): Int {
       return taskList.size
    }

    fun finishItemTask(task: ArrayList<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }

    fun deleteToSwipe(pos: Int): Task {
        return taskList.get(pos)
    }


}



