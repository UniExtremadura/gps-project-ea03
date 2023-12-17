package com.example.familycoin.repository

import com.example.familycoin.R
import com.example.familycoin.database.RewardDao
import com.example.familycoin.gridView.ShopItem
import com.example.familycoin.gridView.TaskItem
import com.example.familycoin.model.Family
import com.example.familycoin.model.Reward
import com.example.familycoin.model.User

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

    suspend fun createReward(rewardName: String, assignedUserName: String?, familyCoinId: Long, cost: Int?, description: String) {
        if (cost != null) {
            val newReward = Reward(
                rewardName = rewardName,
                assignedUserName = assignedUserName,
                familyCoinId = familyCoinId,
                cost = cost,
                description = description,
                imageUrl = R.drawable.reward
            )
            insertReward(newReward)
        } else {
            throw Exception("Cost must be a number (Integer)")
        }
    }

    suspend fun createRewardList(user: User): ArrayList<ShopItem> {
        val rewardList: ArrayList<ShopItem> = ArrayList()
        val rewardListUser = getRewardsByFamilyCoinId(user.familyCoinId!!)
        return if (rewardListUser != null && rewardListUser.isNotEmpty()) {
            for (reward in rewardListUser) {
                rewardList.add(ShopItem(reward.rewardName, R.drawable.baseline_task_24))
            }
            rewardList
        } else {
            rewardList
        }
    }
}
