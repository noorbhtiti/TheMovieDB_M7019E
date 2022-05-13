package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieCacheDao
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieListViewModel(private val movieDatabaseDao: MovieDatabaseDao,private val movieCacheDao: MovieCacheDao, application: Application) :
    AndroidViewModel(application) {

    private val moviesRepository = MoviesRepository(movieCacheDao, movieDatabaseDao)

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }


    var _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>>
        get() {
            return _movies
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
                 moviesRepository.refreshPopularMovies()
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
                moviesRepository.refreshTopRatedMovies()
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movies.value = arrayListOf()
            }
        }
    }


    fun getSavedMovies(){
        viewModelScope.launch {
            moviesRepository.refreshSavedMovies()
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