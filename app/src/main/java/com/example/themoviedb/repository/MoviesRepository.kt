package com.example.themoviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.database.MovieDatabase
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieContainer
import com.example.themoviedb.database.asDomainModel
import com.example.themoviedb.model.asDatabaseModel
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepository(private val database: MovieDatabase) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.movieDatabaseDao.getAllMovies()) {
        it.asDomainModel()
    }

    suspend fun refreshPopularMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh popular reviews is called")
            val movieResponse = TMDBApi.movieListRetrofitService.getPopularMovies()
            val movieList = MovieContainer(movieResponse.results)
            database.movieDatabaseDao.insertAll(movieList.asDatabaseModel())
        }
    }

    suspend fun refreshTopRatedMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh top rated reviews is called")
            val movieResponse = TMDBApi.movieListRetrofitService.getTopRatedMovies()
            val movieList = MovieContainer(movieResponse.results)
            database.movieDatabaseDao.insertAll(movieList.asDatabaseModel())
        }
    }
}


