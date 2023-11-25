package com.example.familycoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.familycoin.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Query("SELECT type FROM user WHERE name LIKE :name LIMIT 1")
    suspend fun getType(name: String): Int

    @Query("SELECT * FROM user WHERE familyCoinId LIKE :familyCoinId")
    suspend fun findByFamilyCoinId(familyCoinId: Long): List<User>


    @Query("SELECT coins FROM user WHERE name LIKE :name LIMIT 1")
    suspend fun getCoins(name: String): Int

    @Query("SELECT familyCoinId FROM user WHERE name LIKE :name LIMIT 1")
    suspend fun getFamilyCoinId(name: String): Long

    @Update
    suspend fun update(user: User)

    @Insert
    suspend fun insert(user: User): Long
}