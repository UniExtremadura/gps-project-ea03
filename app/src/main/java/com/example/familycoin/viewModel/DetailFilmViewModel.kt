package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.model.Reward
import com.example.familycoin.model.Task
import com.example.familycoin.model.User
import com.example.familycoin.repository.RewardRepository
import com.example.familycoin.repository.TaskRepository
import com.example.familycoin.repository.UserRepository

class DetailFilmViewModel (private var rewardRepository: RewardRepository, private var userRepository: UserRepository): ViewModel() {

    suspend fun updateUserRewards(user: User, cost: Int){
        userRepository.updateUser(rewardRepository.reedemReward(user, cost))
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
                return DetailFilmViewModel(
                    (application as FamilyCoinApplication).appContainer.rewardRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository,
                ) as T
            }
        }
    }
}