package com.example.themoviedb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.*
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.TMDBApi
import timber.log.Timber
import java.io.IOException

class MovieRepository(private val databaseDao: MovieDatabaseDao) {

    val movies = MutableLiveData<List<Movie>>()

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    suspend fun refreshPopularMovies() {
            Timber.d("Get Popular is called")
            try {
                val results = TMDBApi.movieListRetrofitService.getPopularMovies().results
                movies.value = results
                Timber.d(movies.value.toString() )
                val dbEntries = results.asPopularMovieDatabaseModel()
                _dataFetchStatus.value = DataFetchStatus.DONE
                databaseDao.insertPopular(dbEntries)
                databaseDao.deleteTopRated()
            }catch (networkError: IOException) {
                val results = databaseDao.getPopularMovies().asPopularMovieDomainModel()
                movies.value = results
                if (results.isNullOrEmpty()) {
                    _dataFetchStatus.value = DataFetchStatus.ERROR
                }else{
                    _dataFetchStatus.value = DataFetchStatus.DONE
                }
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
                val dbEntries = results.asTopRatedMovieDatabaseModel()
                _dataFetchStatus.value = DataFetchStatus.DONE
                databaseDao.insertTopRated(dbEntries)
                databaseDao.deletePopular()
            }catch (networkError: IOException) {
                val results = databaseDao.getTopRatedMovies().asTopRatedMovieDomainModel()
                movies.value = results
                if (results.isNullOrEmpty()) {
                    _dataFetchStatus.value = DataFetchStatus.ERROR
                }else{
                    _dataFetchStatus.value = DataFetchStatus.DONE
                }
                Timber.d(movies.value.toString())
            }
            //databaseDao.insertTopRated(results.asTopRatedMovieDatabaseModel())
            //val results:List<Movie> = TMDBApi.movieListRetrofitService.getTopRatedMovies().results
    }

    suspend fun getSavedMovies()  {
        movies.value = databaseDao.getAllMovies()
    }
}
