package com.example.themoviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.database.ReviewsDatabase
import com.example.themoviedb.database.asDomainModel
import com.example.themoviedb.model.Review
import com.example.themoviedb.model.ReviewContainer
import com.example.themoviedb.model.asDatabaseModel
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ReviewsRepository(private val database: ReviewsDatabase) {

    val reviews: LiveData<List<Review>> = Transformations.map(database.reviewDao.getListOfReviews()) {
        it.asDomainModel()
    }

    suspend fun refreshReviews(movie_id : String) {
        withContext(Dispatchers.IO) {
            Timber.d("refresh reviews is called")
            val reviewResponse = TMDBApi.movieListRetrofitService.getMovieReviews(movie_id)
            val reviewList = ReviewContainer(reviewResponse.results)
            database.reviewDao.insertAll(reviewList.asDatabaseModel())
        }
    }
}