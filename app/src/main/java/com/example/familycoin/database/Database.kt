package com.example.familycoin.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.familycoin.model.User

@androidx.room.Database(entities = [User::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao

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