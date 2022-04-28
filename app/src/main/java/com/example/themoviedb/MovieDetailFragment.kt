package com.example.themoviedb

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.database.MovieDatabase
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.databinding.FragmentMoiveListBinding
import com.example.themoviedb.databinding.FragmentMovieDetailBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieDetails
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.viewmodel.MovieDetailViewModel
import com.example.themoviedb.viewmodel.MovieDetailViewModelFactory
import com.example.themoviedb.viewmodel.MovieListViewModel
import com.example.themoviedb.viewmodel.MovieListViewModelFactory


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var viewModelFactory: MovieDetailViewModelFactory
    private lateinit var movieDatabaseDao: MovieDatabaseDao


    private lateinit var movie : Movie
    private lateinit var movieDetails: MovieDetails


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        movie= MovieDetailFragmentArgs.fromBundle(requireArguments()).movie
        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()
        viewModelFactory = MovieDetailViewModelFactory(movieDatabaseDao, application,movie)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieDetailViewModel::class.java]
        binding.movie = movie
        binding.detailsModel = viewModel
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backToMovieList.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionSecondFragmentToFirstFragment())
        }

        binding.goToThirdFragment.setOnClickListener {
            findNavController().navigate(MovieDetailFragmentDirections.actionSecondFragmentToThirdFragment())
        }

        binding.imdbButton.setOnClickListener {
            val uri: Uri = Uri.parse(Constants.IMDB_URL+ movieDetails.imdb_id) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}