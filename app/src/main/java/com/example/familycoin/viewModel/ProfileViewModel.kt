package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.User
import com.example.familycoin.repository.FamilyRepository
import com.example.familycoin.repository.UserRepository

class ProfileViewModel (private var familyRepository: FamilyRepository, private var userRepository: UserRepository): ViewModel() {

    suspend fun getFamilyGroup(user: User): String {
        return familyRepository.getFamilyGroup(getFamily(user))
    }

    private suspend fun getFamily(user: User): Long? {
        return userRepository.getFamilyCoinIdByName(user.name)
    }

    suspend fun getUserCoins(user: User): String {
        return userRepository.getCoinsByName(user.name).toString()
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
                return ProfileViewModel(
                    (application as FamilyCoinApplication).appContainer.familyRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository,
                ) as T
            }
        }
    }
}