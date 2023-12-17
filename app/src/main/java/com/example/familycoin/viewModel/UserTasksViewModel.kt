package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.gridView.UserTaskItem
import com.example.familycoin.model.User
import com.example.familycoin.repository.TaskRepository
import com.example.familycoin.repository.UserRepository

class UserTasksViewModel(private var taskRepository: TaskRepository): ViewModel()  {

    suspend fun createUserTasksList(username: String): ArrayList<UserTaskItem> {
        return taskRepository.createUserTasksList(username)
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
                return UserTasksViewModel(
                    (application as FamilyCoinApplication).appContainer.taskRepository
                ) as T
            }
        }
    }
}