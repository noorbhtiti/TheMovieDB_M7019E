package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie

class MovieVideosViewModelFactory(
    private val movieDatabaseDao: MovieDatabaseDao,
    private val application: Application,
    private val movie: Movie
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieVideosViewModel::class.java)) {
            return MovieVideosViewModel(movieDatabaseDao, application, movie) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
