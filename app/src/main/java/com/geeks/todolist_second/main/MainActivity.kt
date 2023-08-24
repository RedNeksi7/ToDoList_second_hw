package com.geeks.todolist_second.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geeks.todolist_second.R
import com.geeks.todolist_second.model.TaskViewModel
import com.geeks.todolist_second.adapter.TaskAdapter
import com.geeks.todolist_second.databinding.ActivityMainBinding
import com.geeks.todolist_second.model.Filter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        binding.viewModel = viewModel

        taskAdapter = TaskAdapter(viewModel)
        binding.recycleView.apply {
            adapter = taskAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.addTaskBt.setOnClickListener {
            val taskTitle = binding.taskEt.text.toString()
            if (taskTitle.isNotEmpty()) {
                viewModel.addTask(taskTitle)
                binding.taskEt.text.clear()
            }
        }


        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_all -> viewModel.setFilter(Filter.ALL)
                R.id.radio_completed -> viewModel.setFilter(Filter.COMPLETED)
                R.id.radio_incomplete -> viewModel.setFilter(Filter.INCOMPLETE)
            }
        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> viewModel.setFilter(Filter.ALL)
                    1 -> viewModel.setFilter(Filter.COMPLETED)
                    2 -> viewModel.setFilter(Filter.INCOMPLETE)
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.filteredTasks.observe(this, { tasks ->
            taskAdapter.tasks = tasks
            taskAdapter.notifyDataSetChanged()
        })
    }
}