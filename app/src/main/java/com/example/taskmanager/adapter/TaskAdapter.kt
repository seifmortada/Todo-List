package com.example.taskmanager.adapter

import android.content.Context
import android.renderscript.RenderScript.Priority
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.fragments.all.TaskFragmentDirections
import com.example.taskmanager.utils.Task
import com.example.taskmanager.viewmodel.TaskViewModel

class TaskAdapter(private val viewModel: TaskViewModel, private val context: Context) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }


    override fun getItemCount(): Int = taskList.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = taskList[position]
        val taskNameTextView: TextView = holder.itemView.findViewById(R.id.taskNameTxt)
        val dueDateTextView: TextView = holder.itemView.findViewById(R.id.taskDateTxt)
        val priorityTextView: TextView = holder.itemView.findViewById(R.id.taskPriority)
        val layout: ConstraintLayout = holder.itemView.findViewById(R.id.task_item_layout)
        val checkBox: CheckBox = holder.itemView.findViewById(R.id.taskCompletedCb)
        val line = holder.itemView.findViewById<View>(R.id.completedLine)

        taskNameTextView.text = task.name
        dueDateTextView.text = task.dueDate.toString()
        priorityTextView.text = task.priority.toString()

        if (task.completed)
            line.visibility = View.VISIBLE
        else
            line.visibility = View.INVISIBLE
        when (task.priority) {
            1 -> {
                priorityTextView.background.setTint(context.getColor(R.color.greenLow))
                priorityTextView.text = "Low"
            }

            2 -> {
                priorityTextView.background.setTint(context.getColor(R.color.yellowMedium))
                priorityTextView.text = "Medium"
            }

            else -> {
                priorityTextView.background.setTint(context.getColor(R.color.redHigh))
                priorityTextView.text = "High"
            }
        }
        layout.setOnClickListener {
            val action = TaskFragmentDirections.actionTaskFragmentToTaskDetailsFragment(task)
            holder.itemView.findNavController().navigate(action)
        }
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                task.completed = true
                line.visibility = View.VISIBLE
                viewModel.updateTask(task)
            } else {
                task.completed = false
                line.visibility = View.INVISIBLE
                viewModel.updateTask(task)
            }
        }

    }

    fun setData(task:List<Task>) {
    this.taskList=task
        notifyDataSetChanged()
    }

}

