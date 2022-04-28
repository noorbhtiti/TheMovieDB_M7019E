package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.MovieDetailsResponse
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieDatabaseDao: MovieDatabaseDao,
    application: Application,
    movie: Movie
) : AndroidViewModel(application){

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() {
            return _isFavorite
        }

    init {
        getMovieDetails(movie.id.toString())
        setIsFavorite(movie)
    }

    private fun setIsFavorite(movie: Movie) {
        viewModelScope.launch {
            _isFavorite.value = movieDatabaseDao.isFavorite(movie.id)
        }
    }


    private val _movieDetails = MutableLiveData<MovieDetailsResponse?>()
    val movieDetails: LiveData<MovieDetailsResponse?>
        get() {
            return _movieDetails
        }


    fun getMovieDetails(movie_id: String){
        viewModelScope.launch {
            try {
                val movieDetailsResponse: MovieDetailsResponse = TMDBApi.movieListRetrofitService.getMovieDetails(movie_id)
                _movieDetails.value = movieDetailsResponse
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieDetails.value = null
            }
        }
    }

}