package com.example.familycoin.api

import com.example.familycoin.database.FilmDao

class Repository private constructor(
    private val filmDao: FilmDao,
    private val networkService: MovieApiService
) {
    private var lastUpdateTimeMillis: Long = 0L

    val films = filmDao.getFilms()
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
    private suspend fun shouldUpdateFilmsCache(): Boolean {
        val lastFetchTimeMillis = lastUpdateTimeMillis
        val timeFromLastFetch = System.currentTimeMillis() - lastFetchTimeMillis
        return timeFromLastFetch > MIN_TIME_FROM_LAST_FETCH_MILLIS || filmDao.getNumberOfFilms() == 0L
    }
    companion object {
        private const val MIN_TIME_FROM_LAST_FETCH_MILLIS: Long = 30000
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(
            filmDao: FilmDao,
            movieApiService: MovieApiService
        ): Repository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(filmDao, movieApiService).also { INSTANCE = it }
            }
        }
    }
}