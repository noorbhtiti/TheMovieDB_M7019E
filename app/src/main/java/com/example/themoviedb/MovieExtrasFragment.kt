package com.example.themoviedb

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.adapter.MovieReviewAdapter
import com.example.themoviedb.adapter.MovieVideoAdapter
import com.example.themoviedb.database.MovieDatabase
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.databinding.FragmentMovieExtrasBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.viewmodel.*
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieExtrasFragment : Fragment() {



    private var _binding: FragmentMovieExtrasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelReview: MovieReviewsViewModel
    private lateinit var viewModelReviewFactory: MovieReviewsViewModelFactory

    private lateinit var viewModelVideo: MovieVideosViewModel
    private lateinit var viewModelVideoFactory: MovieVideosViewModelFactory

    private lateinit var movieDatabaseDao: MovieDatabaseDao
    private lateinit var movie : Movie


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieExtrasBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        movie= MovieExtrasFragmentArgs.fromBundle(requireArguments()).movie
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()

        viewModelReviewFactory = MovieReviewsViewModelFactory(movieDatabaseDao, application,movie)
        viewModelReview = ViewModelProvider(this, viewModelReviewFactory)[MovieReviewsViewModel::class.java]

        viewModelVideoFactory = MovieVideosViewModelFactory(movieDatabaseDao, application,movie)
        viewModelVideo = ViewModelProvider(this, viewModelVideoFactory)[MovieVideosViewModel::class.java]



        val movieReviewAdapter = MovieReviewAdapter()
        val movieVideoAdapter = MovieVideoAdapter()

        binding.movieExtrasReviewRv.adapter = movieReviewAdapter
        viewModelReview.movieReviews.observe(viewLifecycleOwner){ reviewList ->
            reviewList?.let {
                movieReviewAdapter.submitList(reviewList)
            }
        }

        binding.movieExtrasVideoRv.adapter = movieVideoAdapter
        viewModelVideo.movieVideos.observe(viewLifecycleOwner){ videoList ->
            videoList?.let {
                movieVideoAdapter.submitList(videoList)
            }
        }



        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}