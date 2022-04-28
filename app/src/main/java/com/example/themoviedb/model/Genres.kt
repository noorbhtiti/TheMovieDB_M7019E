package com.example.themoviedb.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class Genres(
    @Json(name = "id")
    var id :Int,

    @Json(name = "name")
    var name : String,

) : Parcelable
