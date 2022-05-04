package com.example.themoviedb.network

import com.example.themoviedb.database.ReviewDatabase
import com.example.themoviedb.model.Genres
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkReviewContainer(val reviews: List<MovieReviewsResponse>)

@JsonClass(generateAdapter = true)
class MovieReviewsResponse {

    @Json(name = "id")
    var id: Int = 0

    @Json(name = "page")
    var page: Int = 0

    @Json(name = "results")
    var results: List<Review> = listOf()

    @Json(name = "total_pages")
    var total_pages: Int = 0

    @Json(name = "total_results")
    var total_results: Int = 0

}


