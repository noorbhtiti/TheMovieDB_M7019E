package com.example.themoviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.themoviedb.adapter.MovieReviewAdapter
import com.example.themoviedb.database.MovieDatabase
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.database.ReviewDatabaseDao
import com.example.themoviedb.database.getReviewDatabase
import com.example.themoviedb.databinding.FragmentMovieExtrasBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.viewmodel.MovieReviewsViewModel
import com.example.themoviedb.viewmodel.MovieReviewsViewModelFactory


class MovieExtrasFragment : Fragment() {


    private var _binding: FragmentMovieExtrasBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelReview: MovieReviewsViewModel
    private lateinit var viewModelReviewFactory: MovieReviewsViewModelFactory
    private lateinit var reviewDatabaseDao: ReviewDatabaseDao
    private lateinit var movie : Movie

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieExtrasBinding.inflate(inflater, container, false)
        val application = requireNotNull(this.activity).application
        movie= MovieExtrasFragmentArgs.fromBundle(requireArguments()).movie

        reviewDatabaseDao = getReviewDatabase(application).reviewDao
        viewModelReviewFactory = MovieReviewsViewModelFactory(reviewDatabaseDao, application,movie)
        viewModelReview = ViewModelProvider(this, viewModelReviewFactory)[MovieReviewsViewModel::class.java]

        val movieReviewAdapter = MovieReviewAdapter()

        binding.movieExtrasReviewRv.adapter = movieReviewAdapter
        viewModelReview.movieReviews.observe(viewLifecycleOwner){ reviewList ->
            reviewList?.let {
                movieReviewAdapter.submitList(reviewList)
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.BackToHome.setOnClickListener {
//            findNavController().navigate(MovieExtrasFragmentDirections.actionThirdFragmentToFirstFragment())
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}