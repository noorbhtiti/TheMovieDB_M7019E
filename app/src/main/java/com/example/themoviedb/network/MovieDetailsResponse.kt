package com.example.themoviedb.network

import com.example.themoviedb.model.Genres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class MovieDetailsResponse {

    @Json(name = "homepage")
    lateinit var homepage: String

    @Json(name = "imdb_id")
    lateinit var imdb_id: String

    @Json(name = "genres")
    var genres: List<Genres> = listOf()


}