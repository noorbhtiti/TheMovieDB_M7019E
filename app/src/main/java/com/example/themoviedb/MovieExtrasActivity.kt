package com.example.themoviedb

import android.os.Bundle
import android.os.PersistableBundle
import com.example.themoviedb.databinding.MovieVideoItemBinding
import com.example.themoviedb.utils.Constants
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer

class MovieExtrasActivity: YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private var _binding: MovieVideoItemBinding? = null
    private val binding get() = _binding!!

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if(!wasRestored){
            player?.cueVideo("wKJ9KzGQq0w")
        }
    }

    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.movie_video_item)
        _binding = MovieVideoItemBinding.inflate(layoutInflater)
        binding.ytPlayer.initialize(Constants.API_KEY_YOUTUBE, this)

    }
}