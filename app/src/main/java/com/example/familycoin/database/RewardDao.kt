package com.example.familycoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.familycoin.model.Reward

@Dao
interface RewardDao {

    @Query("SELECT * FROM reward WHERE rewardId LIKE :rewardId LIMIT 1")
    suspend fun findById(rewardId: Long): Reward

    @Query("SELECT * FROM reward WHERE familyCoinId LIKE :familyCoinId")
    suspend fun findByFamilyCoinId(familyCoinId: Long): List<Reward>

    @Query("SELECT * FROM reward WHERE assignedUserName LIKE :assignedUserName LIMIT 1")
    suspend fun findByAssignedUserName(assignedUserName: String): Reward

    @Insert
    suspend fun insert(reward: Reward): Long

    @Update
    suspend fun updateTaskUser(reward: Reward)
}