package com.example.familycoin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Family (
    @PrimaryKey(autoGenerate = true) val familyCoinId: Long = 0,
    val familyName: String = ""
) : Serializable