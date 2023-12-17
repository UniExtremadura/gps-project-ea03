package com.example.familycoin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.familycoin.model.Family

@Dao
interface FamilyDao {

    @Query("SELECT * FROM family WHERE familyCoinId LIKE :familyCoinId LIMIT 1")
    suspend fun findById(familyCoinId: Long): Family?

    @Insert
    suspend fun insert(family: Family): Long

    @Query("SELECT * FROM family WHERE familyName LIKE :familyName LIMIT 1")
    suspend fun findByName(familyName: String): Family
}