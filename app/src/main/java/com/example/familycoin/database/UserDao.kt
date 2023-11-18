package com.example.familycoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.familycoin.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE name LIKE :first LIMIT 1")
    suspend fun findByName(first: String): User

    @Query("SELECT type FROM user WHERE name LIKE :name LIMIT 1")
    suspend fun getType(name: String): Int
    @Insert
    suspend fun insert(user: User): Long
}