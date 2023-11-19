package com.example.familycoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.familycoin.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE taskId LIKE :taskId LIMIT 1")
    suspend fun findById(taskId: Long): Task

    @Query("SELECT * FROM task WHERE familyCoinId LIKE :familyCoinId")
    suspend fun findByFamilyCoinId(familyCoinId: Long): List<Task>

    @Query("SELECT * FROM task WHERE assignedUserName LIKE :assignedUserName LIMIT 1")
    suspend fun findByAssignedUserName(assignedUserName: String): Task

    @Update
    suspend fun updateAssignedUser(task: Task)

    @Insert
    suspend fun insert(task: Task): Long
}