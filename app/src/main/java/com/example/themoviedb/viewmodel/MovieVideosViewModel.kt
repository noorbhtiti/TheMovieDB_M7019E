package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.Video
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.MovieVideosResponse
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch


class MovieVideosViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application, movie: Movie) :
    AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }


    private val _movieVideos = MutableLiveData<List<Video>>()
    val movieVideos: LiveData<List<Video>>
        get() {
            return _movieVideos
        }

    init {
        getMovieVideos(movie.id.toString())
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }


    fun getMovieVideos(movie_id: String){
        viewModelScope.launch {
            try {
                val movieVideosResponse: MovieVideosResponse = TMDBApi.movieListRetrofitService.getMovieVideos(movie_id)
                _movieVideos.value = movieVideosResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieVideos.value = arrayListOf()
            }
        }
    }
}