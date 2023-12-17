package com.example.familycoin.repository

import com.example.familycoin.R
import com.example.familycoin.database.RewardDao
import com.example.familycoin.model.Family
import com.example.familycoin.model.Reward

class RewardRepository(private val rewardDao: RewardDao) {

    suspend fun insertInitialsRewards(family: Family) {
        val newReward1 = Reward(rewardName = "Films", description = "List of films", cost = 100, familyCoinId = family.familyCoinId, assignedUserName = null, imageUrl = R.drawable.baseline_movie_24)
        insertReward(newReward1)
        val newReward2 = Reward(rewardName = "PortAventura", description = "Go to PortAventura", cost = 50000, familyCoinId = family.familyCoinId, assignedUserName = null, imageUrl = R.drawable.portaventura)
        insertReward(newReward2)
    }


    suspend fun findRewardById(rewardId: Long): Reward {
        return rewardDao.findById(rewardId)
    }

    suspend fun getRewardsByFamilyCoinId(familyCoinId: Long): List<Reward> {
        return rewardDao.findByFamilyCoinId(familyCoinId)
    }

    suspend fun findRewardByName(rewardName: String): Reward {
        return rewardDao.findByName(rewardName)
    }

    suspend fun findRewardByAssignedUserName(assignedUserName: String): Reward {
        return rewardDao.findByAssignedUserName(assignedUserName)
    }

    suspend fun insertReward(reward: Reward): Long {
        return rewardDao.insert(reward)
    }

    suspend fun updateAssignedUser(reward: Reward) {
        rewardDao.updateAssignedUser(reward)
    }
}
