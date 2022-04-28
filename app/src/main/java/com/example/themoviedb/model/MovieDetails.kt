package com.example.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetails(
    @Json(name = "id")
    var id :Int,

    @Json(name = "homepage")
    var homepage : String,

    @Json(name = "imdb_id")
    var imdb_id : String,

    @Json(name = "genres")
    var results: List<Genres> = listOf()




) : Parcelable