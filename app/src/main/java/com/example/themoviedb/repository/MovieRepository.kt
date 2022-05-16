package com.example.themoviedb.repository

import androidx.lifecycle.MutableLiveData
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.*
import com.example.themoviedb.network.TMDBApi
import timber.log.Timber

class MovieRepository(private val databaseDao: MovieDatabaseDao) {

    val movies = MutableLiveData<List<Movie>>()

    suspend fun refreshPopularMovies() {
            Timber.d("Get Popular is called")
            try {
                val results = TMDBApi.movieListRetrofitService.getPopularMovies().results
                movies.value = results
                Timber.d(movies.value.toString() )
                databaseDao.insertPopular(results.asPopularMovieDatabaseModel())
            }catch (e: java.net.UnknownHostException) {
                val results = databaseDao.getPopularMovies().value?.asPopularMovieDomainModel()
                movies.value = results!!
                Timber.d(movies.value.toString() )
            }
            //val response: MovieContainer =
            //MovieContainer(TMDBApi.movieListRetrofitService.getPopularMovies().results).asDomainModel()
    }

    suspend fun refreshTopRatedMovies() {
            Timber.d("Get Top Rated is called")
            try {
                val results = TMDBApi.movieListRetrofitService.getTopRatedMovies().results
                movies.value = results
                Timber.d(movies.value.toString())
                databaseDao.insertTopRated(results.asTopRatedMovieDatabaseModel())
            }catch (e:Exception) {
                val results = databaseDao.getTopRatedMovies().value?.asTopRatedMovieDomainModel()
                movies.value = results!!
                Timber.d(movies.value.toString())
            }
            //databaseDao.insertTopRated(results.asTopRatedMovieDatabaseModel())
            //val results:List<Movie> = TMDBApi.movieListRetrofitService.getTopRatedMovies().results
    }
}
