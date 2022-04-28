package com.example.themoviedb

import android.annotation.SuppressLint
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
import com.example.themoviedb.databinding.FragmentMovieDetailBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.utils.Constants
import com.example.themoviedb.viewmodel.MovieDetailViewModel
import com.example.themoviedb.viewmodel.MovieDetailViewModelFactory


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
    private lateinit var imdb_id: String


    @SuppressLint("SetTextI18n")
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
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            it?.let{
                binding.detailsModel = it
                imdb_id = it.imdb_id
            }
        }
        viewModel.moviesGenres.observe(viewLifecycleOwner){ genresList ->
            val mutableList = mutableListOf<String>()
            val genresText = "Genre(s): "
            genresList?.forEach { genres ->
                mutableList.add(genres.name)
            }
            binding.movieDetailGenres.text = genresText + mutableList.joinToString(", ")
        }
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
            val uri: Uri = Uri.parse(Constants.IMDB_URL+imdb_id) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}