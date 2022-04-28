package com.example.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey()
    @Json(name = "id")
    var id :Long,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title : String,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath : String,

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    var backdropPath : String,

    @ColumnInfo(name = "release_data")
    @Json(name = "release_date")
    var releaseData : String,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview : String,



) : Parcelable