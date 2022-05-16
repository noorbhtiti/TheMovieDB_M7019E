package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.repository.MovieRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.IOException

abstract class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val movieRepository = MovieRepository(movieDatabaseDao)

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return movieRepository.dataFetchStatus
        }


    private val _movies = MutableLiveData<List<Movie>>()
    //val movies = movieRepository.movies
    val movies: LiveData<List<Movie>>
        get() {
            return movieRepository.movies
        }


    private val _navigateToMovieDetail = MutableLiveData<Movie?>()
    val navigateToMovieDetail: MutableLiveData<Movie?>
        get() {
            return _navigateToMovieDetail
        }


    init {
        getPopularMovies()
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    fun getPopularMovies(){
        viewModelScope.launch(SupervisorJob())  {
             try {
                 //val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getPopularMovies()
                 //_movies.value = movieResponse.results
                 movieRepository.refreshPopularMovies()
             }catch (networkError: IOException){
             }
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch(SupervisorJob()) {
            try {
                movieRepository.refreshTopRatedMovies()
                //val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getTopRatedMovies()
                //_movies.value = movieResponse.results
            }catch (networkError: IOException){
            }
        }
    }


    fun getSavedMovies(){
        viewModelScope.launch {
            movieRepository.getSavedMovies()
        }
    }

    fun addMovie() {
        viewModelScope.launch {
            movies.value?.let { movieDatabaseDao.insert(it[1])
            }
        }
    }

    fun onMovieListItemClicked(movie: Movie){
        _navigateToMovieDetail.value = movie
    }
    fun onMovieDetailNavigated() {
        _navigateToMovieDetail.value = null
    }
}