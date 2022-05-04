package com.example.themoviedb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.themoviedb.model.Review

@Entity
data class ReviewDatabase constructor(
    @PrimaryKey
    val id : String,
    val author : String,
    val content : String,
    val created_at : String,
    val url : String,
)

fun List<ReviewDatabase>.asDomainModel(): List<Review> {
    return map {
        Review(
            id = it.id,
            author = it.author,
            content = it.content,
            created_at = it.created_at,
            url = it.url,
        )
    }
}

