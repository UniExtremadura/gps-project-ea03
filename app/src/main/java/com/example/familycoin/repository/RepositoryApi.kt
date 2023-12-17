package com.example.familycoin.repository

import com.example.familycoin.api.MovieApiService
import com.example.familycoin.database.FilmDao
import com.example.familycoin.model.FilmModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryApi constructor(
    private val filmDao: FilmDao,
    private val networkService: MovieApiService
) {
    private var lastUpdateTimeMillis: Long = 0L

    suspend fun tryUpdateRecentFilmsCache() {
        if (shouldUpdateFilmsCache()) fetchRecentFilms()
    }
    private suspend fun fetchRecentFilms() {
        try {
            val films = networkService.getMovies().map { it.toFilmModel()}
            filmDao.insertAll(films)
            lastUpdateTimeMillis = System.currentTimeMillis()
        } catch (cause: Throwable) {

        }
    }

    suspend fun getFilms(): List<FilmModel>{
        return withContext(Dispatchers.IO) {
            fetchRecentFilms()
            filmDao.getFilms()
        }
    }
    private suspend fun shouldUpdateFilmsCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || filmDao.getNumberOfFilms() == 0L
    }
    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000
        @Volatile private var INSTANCE: RepositoryApi? = null
        fun getInstance(
            filmDao: FilmDao,
            movieApiService: MovieApiService
        ): RepositoryApi {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: RepositoryApi(filmDao, movieApiService).also { INSTANCE = it }
            }
        }
    }
}