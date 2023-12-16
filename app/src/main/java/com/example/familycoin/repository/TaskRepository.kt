package com.example.familycoin.repository

import com.example.familycoin.database.TaskDao
import com.example.familycoin.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun findTaskById(taskId: Long): Task {
        return taskDao.findById(taskId)
    }

    suspend fun findTaskByName(taskName: String): Task {
        return taskDao.findByName(taskName)
    }

    suspend fun getTasksByFamilyCoinId(familyCoinId: Long): List<Task> {
        return taskDao.findByFamilyCoinId(familyCoinId)
    }

    suspend fun getTasksByUsername(assignedUserName: String): List<Task> {
        return taskDao.findByUsername(assignedUserName)
    }

    suspend fun getTaskByAssignedUserName(assignedUserName: String): Task {
        return taskDao.findByAssignedUserName(assignedUserName)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun updateAssignedUser(task: Task) {
        taskDao.updateAssignedUser(task)
    }

    suspend fun insertTask(task: Task): Long {
        return taskDao.insert(task)
    }
}
