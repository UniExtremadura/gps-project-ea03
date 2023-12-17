package com.example.familycoin.repository

import com.example.familycoin.database.FamilyDao
import com.example.familycoin.model.Family
import com.example.familycoin.model.User

class FamilyRepository(private val familyDao: FamilyDao) {


    suspend fun createFamily(familyName: String) {
        if(findFamilyByName(familyName) == null){
            insertFamily(Family(familyName = familyName))
        }
        else{
            throw Exception("Family already exists")
        }
    }

    suspend fun getFamilyGroup(familyId: Long?): String {
        return if (familyId != null) {
            getFamilyName(familyId)
        } else
            "No family group"
    }

    suspend fun getFamilyName(familyCoinId: Long): String {
        return findFamilyById(familyCoinId).familyName
    }

    suspend fun getFamilyCode(familyCoinId: Long): String {
        return findFamilyById(familyCoinId).familyCoinId.toString()
    }

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
