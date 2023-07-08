package com.example.imageslider.network

import com.example.imageslider.model.Movie
import retrofit2.http.GET

interface ApiService {
    @GET("movielist.json")
    suspend fun getAllMovies(): List<Movie>
}