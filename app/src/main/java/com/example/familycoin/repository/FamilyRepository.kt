package com.example.familycoin.repository

import com.example.familycoin.database.FamilyDao
import com.example.familycoin.model.Family

class FamilyRepository(private val familyDao: FamilyDao) {

    suspend fun findFamilyById(familyCoinId: Long): Family {
        return familyDao.findById(familyCoinId)
    }

    suspend fun insertFamily(family: Family): Long {
        return familyDao.insert(family)
    }

    suspend fun findFamilyByName(familyName: String): Family {
        return familyDao.findByName(familyName)
    }
}
