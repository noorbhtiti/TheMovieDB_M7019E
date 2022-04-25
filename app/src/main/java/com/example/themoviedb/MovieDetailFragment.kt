package com.example.themoviedb

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.themoviedb.databinding.FragmentMovieDetailBinding
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.MovieDetails
import com.example.themoviedb.utils.Constants


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var movie : Movie
    private lateinit var movieDetails: MovieDetails

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        movie= MovieDetailFragmentArgs.fromBundle(requireArguments()).movie

        binding.movie = movie
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
            val uri: Uri = Uri.parse(Constants.IMDB_URL+movie.overview) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}