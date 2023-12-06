package com.example.taskmanager.fragments.all

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.R
import com.example.taskmanager.adapter.TaskAdapter
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.viewmodel.TaskViewModel

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var viewmodel: TaskViewModel
    private lateinit var adapterH:TaskAdapter
    private var attached = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)
        viewmodel = ViewModelProvider(this)[TaskViewModel::class.java]

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapterH = TaskAdapter(viewmodel,requireContext())
        binding.recyclerView.apply {
            adapter = adapterH
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewmodel.readAllData.observe(viewLifecycleOwner) {
            adapterH.setData(it)
        }
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_taskFragment_to_addTaskFragment)
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteMenu) {
            deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            viewmodel.deleteAllTasks()
            Toast.makeText(
                requireContext(),
                "Successfully removed all tasks ",
                Toast.LENGTH_LONG
            ).show()
        }
        builder.setIcon(R.drawable.ic_delete_all)
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete all todos ?")
        builder.setMessage("Are you sure want to delete all todos ?")
        builder.create().show()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

        // Check if the fragment is attached to the activity
        if (attached) {
            val activity = activity as FragmentActivity

            // Update the viewmodel with the activity instance
            viewmodel = ViewModelProvider(activity)[TaskViewModel::class.java]
        }
    }


}
