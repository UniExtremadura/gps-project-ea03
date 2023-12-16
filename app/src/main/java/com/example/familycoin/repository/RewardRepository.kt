package com.example.familycoin.repository

import com.example.familycoin.database.RewardDao
import com.example.familycoin.model.Reward

class RewardRepository(private val rewardDao: RewardDao) {

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
