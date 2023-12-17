package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.repository.RewardRepository
import com.example.familycoin.repository.TaskRepository

class NewRewardViewModel(private var rewardRepository: RewardRepository) : ViewModel() {

    suspend fun createReward(rewardName: String, assignedUserName: String?, familyCoinId: Long, cost: Int?, description: String) {
        rewardRepository.createReward(rewardName, assignedUserName, familyCoinId, cost, description)
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
                return NewRewardViewModel(
                    (application as FamilyCoinApplication).appContainer.rewardRepository,
                ) as T
            }
        }
    }
}