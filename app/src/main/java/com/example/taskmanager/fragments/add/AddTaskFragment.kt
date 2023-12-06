package com.example.taskmanager.fragments.add

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentAddTaskBinding
import com.example.taskmanager.utils.Task
import com.example.taskmanager.viewmodel.TaskViewModel

class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var viewmodel: TaskViewModel
    private var date: String = ""
    private var priority: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_task, container, false)
        viewmodel = ViewModelProvider(this)[TaskViewModel::class.java]
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Get the DatePicker instance
        val dueDateDatePicker: DatePicker = binding.dueDateDatePicker
        dueDateDatePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            date = "$year - ${monthOfYear +1} - $dayOfMonth"
        }

       val taskName = binding.taskNameEditText.text
        binding.priorityRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            priority = when (checkedId) {
                R.id.lowPriorityRadioButton -> {
                    1
                }

                R.id.mediumPriorityRadioButton -> {
                    2
                }

                else -> {
                    3
                }
            }
        }
        binding.saveButton.setOnClickListener {

            if (taskName.toString() =="" || date == "" || priority == 0) {
                Toast.makeText(
                    requireContext(),
                    "Please enter all the data for the task",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            } else {
                val newTask = Task(0, taskName.toString(), date, priority, false)
                viewmodel.addTask(newTask)
                Toast.makeText(
                    requireContext(),
                    "Added successfully",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
            }
        }
    }

}
