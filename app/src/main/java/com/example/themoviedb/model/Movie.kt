package com.example.themoviedb.model

import android.os.Parcelable
import androidx.lifecycle.Transformations.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class MovieContainer(val movies: List<Movie>)

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey()
    @Json(name = "id")
    var id :Long,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title : String?,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath : String?,

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    var backdropPath : String?,

    @ColumnInfo(name = "release_data")
    @Json(name = "release_date")
    var releaseData : String?,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview : String?,
) : Parcelable

@Parcelize
@Entity(tableName = "popularMovies")
data class PopularMovie(
    @PrimaryKey()
    @Json(name = "id")
    var id :Long,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title : String?,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath : String?,

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    var backdropPath : String?,

    @ColumnInfo(name = "release_data")
    @Json(name = "release_date")
    var releaseData : String?,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview : String?,
) : Parcelable

@Parcelize
@Entity(tableName = "topRatedMovies")
data class TopRatedMovie(
    @PrimaryKey()
    @Json(name = "id")
    var id :Long,

    @ColumnInfo(name = "title")
    @Json(name = "title")
    var title : String?,

    @ColumnInfo(name = "poster_path")
    @Json(name = "poster_path")
    var posterPath : String?,

    @ColumnInfo(name = "backdrop_path")
    @Json(name = "backdrop_path")
    var backdropPath : String?,

    @ColumnInfo(name = "release_data")
    @Json(name = "release_date")
    var releaseData : String?,

    @ColumnInfo(name = "overview")
    @Json(name = "overview")
    var overview : String?,
) : Parcelable

fun List<PopularMovie>.asPopularMovieDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            releaseData = it.releaseData,
            overview = it.overview
        )
    }
}

fun List<Movie>.asPopularMovieDatabaseModel(): List<PopularMovie> {
    return map {
        PopularMovie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            releaseData = it.releaseData,
            overview = it.overview
        )
    }
}

fun List<TopRatedMovie>.asTopRatedMovieDomainModel(): List<Movie> {
    return map {
        Movie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            releaseData = it.releaseData,
            overview = it.overview
        )
    }
}

fun List<Movie>.asTopRatedMovieDatabaseModel(): List<TopRatedMovie> {
    return map {
        TopRatedMovie(
            id = it.id,
            title = it.title,
            posterPath = it.posterPath,
            backdropPath = it.backdropPath,
            releaseData = it.releaseData,
            overview = it.overview
        )
    }
}
