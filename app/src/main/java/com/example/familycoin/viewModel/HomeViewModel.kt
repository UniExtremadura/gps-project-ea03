package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.User
import com.example.familycoin.repository.UserRepository

class HomeViewModel (private val userRepository: UserRepository): ViewModel() {

    var userSession: User? = null

    suspend fun getUserCoins(name: String): String {
        return userRepository.getCoinsByName(name).toString()
    }

    suspend fun getUserFamilyCoinId(): Long? {
        return userRepository.getFamilyCoinIdByName(userSession!!.name)
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
                return HomeViewModel(
                    (application as FamilyCoinApplication).appContainer.userRepository
                ) as T
            }
        }
    }
}