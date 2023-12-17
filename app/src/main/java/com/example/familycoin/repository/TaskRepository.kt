package com.example.familycoin.repository

import com.example.familycoin.R
import com.example.familycoin.database.TaskDao
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.model.Task
import com.example.familycoin.model.User

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun createTask(nombre: String, descripcion: String, reward: Int, familyCoinId: Long, assignedUserName: String?){
        val task = Task(
            taskName = nombre,
            description = descripcion,
            reward = reward,
            familyCoinId = familyCoinId,
            assignedUserName = assignedUserName,
        )
        taskDao.insert(task)
    }

    suspend fun createTaskList(user: User): ArrayList<TaskItem> {
        val taskList: ArrayList<TaskItem> = ArrayList()
        val taskListUser = getTasksByFamilyCoinId(user.familyCoinId!!)
        return if (taskListUser != null && taskListUser.isNotEmpty()) {
            var assigned: Boolean
            for (task in taskListUser) {
                assigned = false
                if (task.assignedUserName != null) {
                    assigned = true
                }
                taskList.add(TaskItem(task.taskName, R.drawable.baseline_task_24, assigned))
            }
            taskList
        } else {
            taskList
        }
    }

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


}
