package com.example.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movieDetails")
data class MovieDetails(
    @PrimaryKey()
    @Json(name = "id")
    var id :Int,

    @ColumnInfo(name = "homepage")
    @Json(name = "homepage")
    var homepage : String,

    @ColumnInfo(name = "imdb_id")
    @Json(name = "imdb_id")
    var imdb_id : String,

    @ColumnInfo(name = "genres")
    @Json(name = "genres")
    var results: List<Genres> = listOf()




) : Parcelable