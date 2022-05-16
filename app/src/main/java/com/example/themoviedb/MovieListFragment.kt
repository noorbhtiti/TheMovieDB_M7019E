package com.example.themoviedb

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themoviedb.adapter.MovieListAdapter
import com.example.themoviedb.adapter.MovieListClickListener
import com.example.themoviedb.database.MovieDatabase
import com.example.themoviedb.database.MovieDatabaseDao
import com.example.themoviedb.databinding.FragmentMoiveListBinding
import com.example.themoviedb.network.ConnectionLiveData
import com.example.themoviedb.network.DataFetchStatus
import com.example.themoviedb.viewmodel.MovieListViewModel
import com.example.themoviedb.viewmodel.MovieListViewModelFactory
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
abstract class MovieListFragment : Fragment() {


    private var _binding: FragmentMoiveListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieListViewModel
    private lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var movieDatabaseDao: MovieDatabaseDao
    private lateinit var connectionLiveData: ConnectionLiveData
    private var currentList: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoiveListBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        movieDatabaseDao = MovieDatabase.getDatabase(application).movieDatabaseDao()
        viewModelFactory = MovieListViewModelFactory(movieDatabaseDao, application)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]

        val movieListAdapter = MovieListAdapter(MovieListClickListener { movie ->
            viewModel.onMovieListItemClicked(movie)
        })
        val gridLayoutManager = GridLayoutManager(application, 2)

        // Connection Listener
        connectionLiveData = ConnectionLiveData(application.applicationContext)
        connectionLiveData.observe(viewLifecycleOwner) { isNetworkAvailable ->
            isNetworkAvailable?.let {
                if (it) {
                    if (currentList == 0) {
                        Timber.d("network")
                        viewModel.getPopularMovies()
                    } else if (currentList == 1) {
                        viewModel.getTopRatedMovies()
                    }
                }
            }
        }
        binding.movieListRv.layoutManager = gridLayoutManager
        binding.movieListRv.adapter = movieListAdapter
        viewModel.movies.observe(viewLifecycleOwner) { movieList ->
            movieList?.let {
                movieListAdapter.submitList(movieList)
            }
        }

        viewModel.navigateToMovieDetail.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                this.findNavController().navigate(
                    MovieListFragmentDirections.actionFirstFragmentToSecondFragment(movie)
                )
                viewModel.onMovieDetailNavigated()
            }
        }
        viewModel.dataFetchStatus.observe(viewLifecycleOwner) { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.LOADING -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.loading_animation)
                    }
                    DataFetchStatus.ERROR -> {
                        binding.statusImage.visibility = View.VISIBLE
                        binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusImage.visibility = View.GONE
                    }
                }
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        /**
         *  Handle action bar item clicks here. The action bar will
         *  automatically handle clicks on the Home/Up button, so long
         *  as you specify a parent activity in AndroidManifest.xml.
         */

        when (item.itemId) {
            R.id.action_popular_movies -> {
                currentList = 0
                viewModel.getPopularMovies()
            }
            R.id.action_top_rated_movies -> {
                currentList = 1
                viewModel.getTopRatedMovies()
            }
            R.id.action_saved_movies -> {
                currentList = 2
                viewModel.getSavedMovies()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}