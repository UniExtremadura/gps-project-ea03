package com.example.familycoin.repository

import com.example.familycoin.R
import com.example.familycoin.database.TaskDao
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.gridView.UserTaskItem
import com.example.familycoin.model.Task
import com.example.familycoin.model.User

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun createTask(name: String, description: String, reward: Int?, familyCoinId: Long, assignedUserName: String?){
        if(reward != null) {
            val task = Task(
                taskName = name,
                description = description,
                reward = reward,
                familyCoinId = familyCoinId,
                assignedUserName = assignedUserName,
            )
            taskDao.insert(task)
        }
        else{
            throw Exception("Reward must be a number (Integer)")
        }
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

    suspend fun createUserTasksList(username: String): ArrayList<UserTaskItem> {
        val taskList: ArrayList<UserTaskItem> = ArrayList()
        val taskListUser = getTasksByUsername(username)
        if (taskListUser != null && taskListUser.isNotEmpty()) {
            for (task in taskListUser) {
                taskList.add(UserTaskItem(task.taskName, R.drawable.baseline_task_24))
            }
            return taskList
        }
        return taskList
    }

    suspend fun updateUserTasks(user: User, taskName: String){
        val task = findTaskByName(taskName)
        task.assignedUserName = user.name
        updateAssignedUser(task)
    }

    suspend fun claimReward(taskName: String){
        deleteTask(findTaskByName(taskName))
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
