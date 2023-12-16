package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.User
import com.example.familycoin.repository.UserRepository

class JoinViewModel (private var userRepository: UserRepository): ViewModel() {
    suspend fun register(username: String, password: String, type: Int): User {
        return userRepository.register(username, password, type)
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
                return JoinViewModel(
                    (application as FamilyCoinApplication).appContainer.userRepository
                ) as T
            }
        }
    }
}