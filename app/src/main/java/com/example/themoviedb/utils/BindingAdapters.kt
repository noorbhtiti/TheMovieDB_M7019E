package com.example.themoviedb.utils

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.themoviedb.model.Genres


@BindingAdapter("posterImageUrl")
fun bindPosterImage(imgView: ImageView,imgUrl:String){
    imgUrl.let{ posterPath ->
        Glide
            .with(imgView)
            .load(Constants.POSTER_URL + Constants.POSTER_SIZE + posterPath)
            .into(imgView);
    }
}

@BindingAdapter("BackdropImageUrl")
fun bindBackdropImage(imgView: ImageView, imgUrl:String) {
    imgUrl.let { backdropPath ->
        Glide
            .with(imgView)
            .load(Constants.BACKDROP_IMAGE_BASE_URL + Constants.BACKDROP_IMAGE_WIDTH + backdropPath)
            .into(imgView);
    }
}
