package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.Review
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.network.MovieResponse
import com.example.themoviedb.network.MovieReviewsResponse
import com.example.themoviedb.network.TMDBApi
import kotlinx.coroutines.launch


class MovieReviewsViewModel(private val movieDatabaseDao: MovieDatabaseDao, application: Application,movie: Movie) :
    AndroidViewModel(application) {

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }


    private val _movieReviews = MutableLiveData<List<Review>>()
    val movieReviews: LiveData<List<Review>>
        get() {
            return _movieReviews
        }

    init {
        getMovieReviews(movie.id.toString())
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }


    fun getMovieReviews(movie_id: String){
        viewModelScope.launch {
            try {
                val movieReviewResponse: MovieReviewsResponse = TMDBApi.movieListRetrofitService.getMovieReviews(movie_id)
                _movieReviews.value = movieReviewResponse.results
                _dataFetchStatus.value = DataFetchStatus.DONE
            }catch (e:Exception){
                _dataFetchStatus.value = DataFetchStatus.ERROR
                _movieReviews.value = arrayListOf()
            }
        }
    }
}