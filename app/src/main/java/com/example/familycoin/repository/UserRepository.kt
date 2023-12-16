package com.example.familycoin.repository

import com.example.familycoin.database.UserDao
import com.example.familycoin.model.User
import com.example.familycoin.utils.CredentialCheck

class UserRepository(private val userDao: UserDao) {

    suspend fun login(username: String, password: String): User {
        val user = findUserByName(username)
        if (user != null) {
            val check = CredentialCheck.passwordOk(password, user.password)
            if (check.fail) {
                throw Exception(check.msg)
            } else {
                return user
            }
        } else {
            throw Exception("Invalid username")
        }
    }

    suspend fun register(username: String, password: String, type: Int): User {
        val check = CredentialCheck.join(username, password, password)
        var user: User
        if (check.fail) {
            throw Exception(check.msg)
        } else {
            user = User(
                username,
                password,
                type,
                null,
                0
            )
            if(findUserByName(user.name) != null) {
                throw Exception("Username already exists")
            }
            insertUser(user)
        }
        return user
    }

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

    suspend fun getFamilyCoinIdByName(name: String): Long? {
        return userDao.getFamilyCoinId(name)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }
}
