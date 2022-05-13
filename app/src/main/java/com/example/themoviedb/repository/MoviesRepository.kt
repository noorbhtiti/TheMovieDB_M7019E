package com.example.themoviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.themoviedb.database.*
import com.example.themoviedb.model.*
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepository(private val cacheDao: MovieCacheDao, private val movieDao: MovieDatabaseDao) {

    /*
    Alla movies ska hanteras här
     */
    var movies: LiveData<List<Movie>> = Transformations.map(cacheDao.getMovies()){
        #TODO()
    }

    suspend fun refreshPopularMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh popular reviews is called")
            val movieResponse = TMDBApi.movieListRetrofitService.getPopularMovies()
            val movieList = MovieContainer(movieResponse.results).asDomainModel()
            movies = MutableLiveData<List<Movie>>(movieList)
        }
    }

    suspend fun refreshTopRatedMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh top rated reviews is called")
            val movieResponse = TMDBApi.movieListRetrofitService.getTopRatedMovies()
            val movieList = MovieContainer(movieResponse.results).asDomainModel()
            movies = MutableLiveData<List<Movie>>(movieList)
        }
    }

    suspend fun refreshSavedMovies() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh saved reviews is called")
            movieDao.getAllMovies()
        }
    }
}
