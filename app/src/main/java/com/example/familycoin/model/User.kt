package com.example.familycoin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Family::class,
            parentColumns = ["familyCoinId"],
            childColumns = ["familyCoinId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class User(
    @PrimaryKey
    val name: String = "",
    val password: String = "",
    val type: Int = 0,
    @ColumnInfo(name = "familyCoinId", index = true, defaultValue = "NULL")
    var familyCoinId: Long?,
    var coins: Int = 0
) : Serializable
