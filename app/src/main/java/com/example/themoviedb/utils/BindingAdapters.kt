package com.example.themoviedb.utils

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

//@BindingAdapter("imdbBindUrl")
//fun bindImdbUrl(button: Button,imdb_id:String){
//    imdb_id.let {
//        button.setOnClickListener {  }
//    }
//}
//
//@BindingAdapter("genresBind")
//fun bindGenres(textView: TextView,genres:List<Genres>){
//    genres.let {
//            textView.text = genres.joinToString()
//    }
//}