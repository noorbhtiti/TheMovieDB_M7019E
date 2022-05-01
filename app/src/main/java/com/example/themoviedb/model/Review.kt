package com.example.themoviedb.model

import com.squareup.moshi.Json

data class Review(

    @Json(name = "id")
    var id: String?,

    @Json(name = "author")
    var author :String?,

    @Json(name = "content")
    var content : String?,

    @Json(name = "created_at")
    var created_at : String?,

    @Json(name = "url")
    var url : String?,

    )