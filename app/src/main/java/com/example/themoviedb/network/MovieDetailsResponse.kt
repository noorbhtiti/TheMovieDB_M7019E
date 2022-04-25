package com.example.themoviedb.network

import com.example.themoviedb.model.MovieDetails
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailsResponse {


    @Json(name = "results")
    var results: List<MovieDetails> = listOf()


}