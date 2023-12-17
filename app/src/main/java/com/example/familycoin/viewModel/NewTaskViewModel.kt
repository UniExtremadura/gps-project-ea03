package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.database.Database
import com.example.familycoin.model.Task
import com.example.familycoin.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewTaskViewModel(private var taskRepository: TaskRepository) : ViewModel() {

    suspend fun createTask(name: String, description: String, reward: Int, familyCoinId: Long, assignedUsername: String?) {
        taskRepository.createTask(name, description, reward, familyCoinId, assignedUsername)
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
                return NewTaskViewModel(
                    (application as FamilyCoinApplication).appContainer.taskRepository,
                ) as T
            }
        }
    }
}
