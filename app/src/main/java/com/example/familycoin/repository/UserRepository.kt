package com.example.familycoin.repository

import com.example.familycoin.database.UserDao
import com.example.familycoin.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun findUserByName(name: String): User {
        return userDao.findByName(name)
    }

    suspend fun getUserType(name: String): Int {
        return userDao.getType(name)
    }

    suspend fun getUsersByFamilyCoinId(familyCoinId: Long): List<User> {
        return userDao.findByFamilyCoinId(familyCoinId)
    }

    suspend fun getCoinsByName(name: String): Int {
        return userDao.getCoins(name)
    }

    suspend fun getFamilyCoinIdByName(name: String): Long {
        return userDao.getFamilyCoinId(name)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }
}
