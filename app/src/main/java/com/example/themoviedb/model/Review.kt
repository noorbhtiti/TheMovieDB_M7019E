package com.example.themoviedb.model

import com.example.themoviedb.database.ReviewDatabase
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewContainer(val reviews: List<Review>)

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

fun ReviewContainer.asDomainModel(): List<Review> {
    return reviews.map {
        Review(
            id = it.id,
            author = it.author,
            content = it.content,
            created_at = it.created_at,
            url = it.url
        )
    }
}
fun ReviewContainer.asDatabaseModel(): List<ReviewDatabase> {
    return reviews.map {
        ReviewDatabase(
            id = it.id!!,
            author = it.author!!,
            content = it.content!!,
            created_at = it.created_at!!,
            url = it.url!!
        )
    }
}
