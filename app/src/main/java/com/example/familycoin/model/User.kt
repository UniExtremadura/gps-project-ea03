package com.example.familycoin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey
    val name: String = "",
    val password: String = ""
) : Serializable
