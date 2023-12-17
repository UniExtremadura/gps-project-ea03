package com.example.familycoin.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.example.familycoin.model.FilmModel

@Dao
public interface FilmDao {

    @Query("SELECT * FROM filmModel")
    fun getFilms(): List<FilmModel>
    @Query("SELECT count(*) FROM filmModel")
    suspend fun getNumberOfFilms(): Long
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(films: List<FilmModel>)

}
