package com.example.familycoin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Reward (
    @PrimaryKey(autoGenerate = true) val rewardId: Long = 0,
    val rewardName: String,
    @ColumnInfo(name = "assignedUserName", index = true, defaultValue = "NULL")
    val assignedUserName: String?,
    @ColumnInfo(name = "familyCoinId", index = true)
    val familyCoinId: Long,
    val cost: Int = 0,
    val description : String = ""
)