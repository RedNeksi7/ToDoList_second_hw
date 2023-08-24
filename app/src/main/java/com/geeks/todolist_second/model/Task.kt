package com.geeks.todolist_second.model

data class Task(val id: Int,
                var title: String,
                var isCompleted: Boolean,
                var isEditing: Boolean = false
                )
