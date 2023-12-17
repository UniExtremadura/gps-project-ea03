package com.example.familycoin.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.familycoin.FamilyCoinApplication
import com.example.familycoin.R
import com.example.familycoin.model.User
import com.example.familycoin.recyclerView.FamilyItem
import com.example.familycoin.repository.FamilyRepository
import com.example.familycoin.repository.UserRepository

class FamilyViewModel (private var familyRepository: FamilyRepository, private var userRepository: UserRepository): ViewModel(){

    suspend fun getFamilyName(user: User): String {
        return familyRepository.getFamilyName(user.familyCoinId!!)
    }

    suspend fun getFamilyCode(user: User): String {
        return familyRepository.getFamilyCode(user.familyCoinId!!)
    }

    suspend fun getUsersByFamilyCoinId(user: User): ArrayList<User> {
        return userRepository.getUsersByFamilyCoinId(user.familyCoinId!!)
    }

    suspend fun getListFamilyItem(user: User):ArrayList<FamilyItem>{
        val listFamilyItem: ArrayList<FamilyItem> = ArrayList()
        for (user in getUsersByFamilyCoinId(user)) {
            listFamilyItem.add(FamilyItem(user.name, R.drawable.baseline_person_outline_24, user.type))
        }
        return listFamilyItem
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
                return FamilyViewModel(
                    (application as FamilyCoinApplication).appContainer.familyRepository,
                    (application as FamilyCoinApplication).appContainer.userRepository,
                ) as T
            }
        }
    }
}