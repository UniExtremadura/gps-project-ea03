package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.Task
import com.example.familycoin.model.User
import com.example.familycoin.repository.TaskRepository
import com.example.familycoin.repository.UserRepository

class TaskItemDescriptionViewModel (private var taskRepository: TaskRepository): ViewModel() {

    suspend fun getTask(taskName: String): Task {
        return taskRepository.findTaskByName(taskName)
    }

    suspend fun updateUserTasks(user: User, taskName: String){
        taskRepository.updateUserTasks(user, taskName)
    }
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T { // Get the Application object from extras

                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                return TaskItemDescriptionViewModel(
                    (application as FamilyCoinApplication).appContainer.taskRepository
                ) as T
            }
        }
    }
}