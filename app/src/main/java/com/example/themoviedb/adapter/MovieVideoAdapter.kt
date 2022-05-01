package com.example.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.databinding.MovieReviewItemBinding
import com.example.themoviedb.databinding.MovieVideoItemBinding
import com.example.themoviedb.model.Review
import com.example.themoviedb.model.Video
import com.google.android.youtube.player.YouTubePlayerView


class MovieVideoAdapter(): ListAdapter<Video, MovieVideoAdapter.ViewHolder>(MovieVideoDiffCallback()) {
    class ViewHolder(private var binding: MovieVideoItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(video: Video){
            binding.video = video
            binding.executePendingBindings()

        }

        companion object {
            fun from (parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieVideoItemBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class MovieVideoDiffCallback : DiffUtil.ItemCallback<Video>(){


    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }

}

