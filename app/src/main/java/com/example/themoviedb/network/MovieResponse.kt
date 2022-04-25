package com.example.themoviedb.network

import com.example.themoviedb.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import timber.log.Timber

@JsonClass(generateAdapter = true)
class MovieResponse {
    @Json(name = "page")
    var page: Int = 0

    @Json(name = "results")
    var results: List<Movie> = listOf()

    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0

}