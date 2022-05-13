package com.example.themoviedb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.database.ReviewDatabaseDao
import com.example.themoviedb.database.asDomainModel
import com.example.themoviedb.database.getReviewDatabase
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.Review
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.repository.ReviewsRepository
import kotlinx.coroutines.launch
import java.io.IOException


class MovieReviewsViewModel(private val movieReviewDao: ReviewDatabaseDao, application: Application, movie: Movie) :
    AndroidViewModel(application) {

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val reviewsRepository = ReviewsRepository(getReviewDatabase(application), movie.id.toString())

    val reviewlist = reviewsRepository.reviews

    private val _dataFetchStatus = MutableLiveData<DataFetchStatus>()
    val dataFetchStatus: LiveData<DataFetchStatus>
        get() {
            return _dataFetchStatus
        }

    /**
     * returns reviews from repository
     */
    private val _movieReviews = MutableLiveData<List<Review>>()
    val movieReviews: LiveData<List<Review>>
        get() {
            return reviewsRepository.movieReviews
        }

    init {
        refreshDataFromRepository(movie.id.toString())
        _dataFetchStatus.value = DataFetchStatus.LOADING
    }

    /**

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
**/
    private fun refreshDataFromRepository(movie_id: String) = viewModelScope.launch {

        try {
            reviewsRepository.refreshReviews(movie_id)
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        } catch (networkError: IOException) {
            if(reviewlist.value.isNullOrEmpty())
                _eventNetworkError.value = true
        }
    }
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}