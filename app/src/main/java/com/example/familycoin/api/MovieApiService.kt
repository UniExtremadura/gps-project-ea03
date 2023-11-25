package com.example.familycoin.api

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.logging.HttpLoggingInterceptor

private val service: MovieApiService by lazy {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://gist.githubusercontent.com/prodrigul/bd16cf373fdef027b9f746c9c69e752b/raw/bf59ce5436f23a1712a9b28cfb704d0711764eec/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    retrofit.create(MovieApiService::class.java)
}

fun getMovieApiService() = service
interface MovieApiService {
    @GET("https://gist.githubusercontent.com/prodrigul/bd16cf373fdef027b9f746c9c69e752b/raw/bf59ce5436f23a1712a9b28cfb704d0711764eec/Film.JSON")
    suspend fun getMovies(): List<Film>
}
