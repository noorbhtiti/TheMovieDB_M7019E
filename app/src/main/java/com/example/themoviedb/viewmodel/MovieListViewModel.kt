package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieDetails
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.MovieDetailsResponse
import com.example.themoviedb.network.MovieResponse
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }


    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
        }

    private val _movieDetails = MutableLiveData<List<MovieDetails>>()
    val movieDetails: LiveData<List<MovieDetails>>
        get() {
            return _movieDetails
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
        viewModelScope.launch {
             try {
                 val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getPopularMovies()
                 _movies.value = movieResponse.results
                 _dataFetchStatus.value = DataFetchStatus.DONE
             }catch (e:Exception){
                 _dataFetchStatus.value = DataFetchStatus.ERROR
                 _movies.value = arrayListOf()
             }
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            try {
                val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getTopRatedMovies()
                _movies.value = movieResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movies.value = arrayListOf()
            }
        }
    }

    fun getMovieDetails(movie_id: String){
        viewModelScope.launch {
            try {
                val movieDetailsResponse:MovieDetailsResponse = TMDBApi.movieListRetrofitService.getMovieDetails(movie_id)
                _movieDetails.value = movieDetailsResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieDetails.value = arrayListOf()
            }
        }
    }



    fun getSavedMovies(){
        viewModelScope.launch {
            _movies.postValue(movieDatabaseDao.getAllMovies())
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