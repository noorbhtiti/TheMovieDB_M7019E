package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.MovieResponse
import com.example.themoviedb.network.TMDBApi
import com.example.themoviedb.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private val movieRepository = MovieRepository(movieDatabaseDao)


    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
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
        viewModelScope.launch {
             try {
                 //val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getPopularMovies()
                 //_movies.value = movieResponse.results
                 movieRepository.refreshPopularMovies()
                 _dataFetchStatus.value = DataFetchStatus.DONE
             }catch (e:Exception){
                 _dataFetchStatus.value = DataFetchStatus.ERROR
             }
        }
    }

    fun getTopRatedMovies(){
        viewModelScope.launch {
            try {
                movieRepository.refreshTopRatedMovies()
                //val movieResponse:MovieResponse = TMDBApi.movieListRetrofitService.getTopRatedMovies()
                //_movies.value = movieResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
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