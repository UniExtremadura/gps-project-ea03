package com.example.familycoin.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.familycoin.model.Family
import com.example.familycoin.model.FilmModel
import com.example.familycoin.model.Task
import com.example.familycoin.model.User
import com.example.familycoin.model.Reward

@androidx.room.Database(entities = [User::class, Family::class, Task::class, Reward::class, FilmModel::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun familyDao(): FamilyDao
    abstract fun taskDao(): TaskDao
    abstract fun rewardDao(): RewardDao
    abstract fun filmDao(): FilmDao

    companion object {
        private var INSTANCE: Database? = null
        fun getInstance(context: Context): Database? {
            if (INSTANCE == null) {
                synchronized(Database::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        Database::class.java, "familycoin.db"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}