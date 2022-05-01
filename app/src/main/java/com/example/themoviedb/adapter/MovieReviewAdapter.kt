package com.example.themoviedb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themoviedb.databinding.MovieReviewItemBinding
import com.example.themoviedb.model.Review

class MovieReviewAdapter(): ListAdapter<Review,MovieReviewAdapter.ViewHolder>(MovieReviewDiffCallback()) {
    class ViewHolder(private var binding: MovieReviewItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(review:Review){
            binding.review = review
            binding.executePendingBindings()

        }

            companion object {
            fun from (parent: ViewGroup) : ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieReviewItemBinding.inflate(layoutInflater,parent,false)
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

class MovieReviewDiffCallback : DiffUtil.ItemCallback<Review>(){


    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem == newItem
    }

}


