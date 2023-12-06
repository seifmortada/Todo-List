package com.example.taskmanager.fragments.details

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskDetailsBinding
import com.example.taskmanager.utils.Task
import com.example.taskmanager.viewmodel.TaskViewModel

class TaskDetailsFragment : Fragment() {
    private val args by navArgs<TaskDetailsFragmentArgs>()
    private lateinit var binding: FragmentTaskDetailsBinding
    private lateinit var viewModel: TaskViewModel
    private var date: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        viewModel = ViewModelProvider(this)[TaskViewModel::class.java]
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setOldData()
        val dueDate = args.task.dueDate
        setDateFromDueDate(dueDate)
        binding.dueDateDatePicker2.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            date = "$year - ${monthOfYear + 1} - $dayOfMonth"
        }
        binding.saveButton2.setOnClickListener {
            updateTask()

        }
    }

    private fun setOldData() {
        binding.apply {
            taskNameEditText2.setText(args.task.name)
            when (args.task.priority) {
                1 -> {
                    lowPriorityRadioButton2.isChecked = true
                }

                2 -> {
                    mediumPriorityRadioButton2.isChecked = true
                }

                else -> {
                    highPriorityRadioButton2.isChecked = true
                }
            }
        }
    }


    private fun updateTask() {
        val name = binding.taskNameEditText2.text.toString()
        val priority: Int
        when (binding.priorityRadioGroup2.checkedRadioButtonId) {
            R.id.lowPriorityRadioButton2 -> priority = 1
            R.id.mediumPriorityRadioButton2 -> priority = 2
            else -> priority = 3
        }

        val updatedTask =
            Task(args.task.id, name, date, priority, false)
        Log.i(
            "newTask",
            "$updatedTask.id ${updatedTask.name} ${updatedTask.priority} ${updatedTask.dueDate} EditText ${binding.taskNameEditText2.text}"
        )
        viewModel.updateTask(updatedTask)
        findNavController().navigate(R.id.action_taskDetailsFragment_to_taskFragment)
    }

    private fun setDateFromDueDate(dueDate: String) {
        val dateParts = dueDate.split(" - ")
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt() - 1
        val dayOfMonth = dateParts[2].toInt()
        binding.dueDateDatePicker2.updateDate(year, month, dayOfMonth)

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenu) {
            deleteTask()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTask() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setIcon(R.drawable.ic_delete_all)
        alertDialog.setPositiveButton("Yes") { _, _ ->
            viewModel.deleteTask(args.task)
            Toast.makeText(requireContext(), "Succfully removed task", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_taskDetailsFragment_to_taskFragment)
        }
        alertDialog.setNegativeButton("NO") { _, _ -> }
        alertDialog.setTitle("Remove Task ?")
        alertDialog.setMessage("Are you sure want to remove the task ?")
        alertDialog.create().show()
    }

}
