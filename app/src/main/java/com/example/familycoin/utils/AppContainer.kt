package com.example.familycoin.utils

import android.content.Context
import com.example.familycoin.api.getMovieApiService
import com.example.familycoin.database.Database
import com.example.familycoin.repository.FamilyRepository
import com.example.familycoin.repository.RewardRepository
import com.example.familycoin.repository.TaskRepository
import com.example.familycoin.repository.UserRepository

class AppContainer (context: Context){
    private val movieApiService = getMovieApiService()
    private val db = Database.getInstance(context!!)
    val userRepository = UserRepository(db!!.userDao())
    val rewardRepository = RewardRepository(db!!.rewardDao())
    val taskRepository = TaskRepository(db!!.taskDao())
    val familyRepository = FamilyRepository(db!!.familyDao())
}