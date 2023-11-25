package com.example.familycoin.api

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("URL_DE_LA_API_AQUI")
    fun getShows(): Call<List<Show>>
}
