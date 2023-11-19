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
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["name"],
            childColumns = ["assignedUserName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Task (
    @PrimaryKey(autoGenerate = true) val taskId: Long = 0,
    val taskName: String,
    @ColumnInfo(name = "assignedUserName", index = true, defaultValue = "NULL")
    val assignedUserName: String?,
    @ColumnInfo(name = "familyCoinId", index = true)
    val familyCoinId: Long
) : Serializable
