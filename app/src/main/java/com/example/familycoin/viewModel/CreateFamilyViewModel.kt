package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.User
import com.example.familycoin.repository.FamilyRepository
import com.example.familycoin.repository.RewardRepository
import com.example.familycoin.repository.UserRepository

class CreateFamilyViewModel (private var familyRepository: FamilyRepository, private var userRepository: UserRepository , private var rewardRepository: RewardRepository): ViewModel(){

    suspend fun createFamily(familyName: String) {
        familyRepository.createFamily(familyName)
    }

    suspend fun updateUserFamilyCoinId(user: User, familyName: String): User{
        return userRepository.updateUserFamilyCoinId(user, familyRepository.findFamilyByName(familyName))
    }

    suspend fun insertInitialsRewards(familyName: String){
        rewardRepository.insertInitialsRewards(familyRepository.findFamilyByName(familyName))
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
                return CreateFamilyViewModel(
                    (application as FamilyCoinApplication).appContainer.familyRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository,
                    (application as FamilyCoinApplication).appContainer.rewardRepository,
                ) as T
            }
        }
    }
}