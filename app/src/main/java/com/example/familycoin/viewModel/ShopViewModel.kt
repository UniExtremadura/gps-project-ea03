package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.gridView.ShopItem
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.model.User
import com.example.familycoin.repository.RewardRepository
import com.example.familycoin.repository.TaskRepository
import com.example.familycoin.repository.UserRepository

class ShopViewModel(private var rewardRepository: RewardRepository, private var userRepository: UserRepository): ViewModel() {

    suspend fun checkFamily(user: User){
        userRepository.checkFamily(user)
    }

    suspend fun createRewardList(user: User): ArrayList<ShopItem> {
        return rewardRepository.createRewardList(user)
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
                return ShopViewModel(
                    (application as FamilyCoinApplication).appContainer.rewardRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository,
                ) as T
            }
        }
    }
}