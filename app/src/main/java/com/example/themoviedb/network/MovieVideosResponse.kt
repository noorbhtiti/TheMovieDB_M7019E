package com.example.themoviedb.network

import com.example.themoviedb.model.Video
import com.squareup.moshi.Json

class MovieVideosResponse {

    @Json(name = "id")
    var id: Int = 0

    @Json(name = "results")
    var results: List<Video> = listOf()
}