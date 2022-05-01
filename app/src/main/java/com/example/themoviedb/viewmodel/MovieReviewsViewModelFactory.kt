package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie


class MovieReviewsViewModelFactory(
    private val movieDatabaseDao: MovieDatabaseDao,
    private val application: Application,
    private val movie: Movie
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieReviewsViewModel::class.java)) {
            return MovieReviewsViewModel(movieDatabaseDao, application, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}