package com.example.taskmanager.utils

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity("task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val dueDate: String,
    val priority: Int,
    var completed: Boolean = false
):Parcelable
