package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.User
import com.example.familycoin.repository.FamilyRepository
import com.example.familycoin.repository.UserRepository

class JoinFamilyViewModel (private var familyRepository: FamilyRepository, private var userRepository: UserRepository): ViewModel()  {

    suspend fun joinFamily(familyCoinId: Long){
        familyRepository.joinFamily(familyCoinId)
    }

    suspend fun updateUserFamilyCoinId(user: User, familyCoinId: Long): User{
        return userRepository.updateUserFamilyCoinId(user, familyRepository.findFamilyById(familyCoinId)!!)
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
                return JoinFamilyViewModel(
                    (application as FamilyCoinApplication).appContainer.familyRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository
                ) as T
            }
        }
    }
}