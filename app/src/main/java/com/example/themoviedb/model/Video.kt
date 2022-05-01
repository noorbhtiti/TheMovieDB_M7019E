package com.example.themoviedb.model

import com.squareup.moshi.Json

data class Video(
    @Json(name = "site")
    var site: String?,

    @Json(name = "key")
    var key :String?,

    @Json(name = "id")
    var id : String?,
)
