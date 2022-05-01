package com.example.themoviedb.network

import com.example.themoviedb.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Add a httpclient logger for debugging purpose
 * object.
 */
fun getLoggerIntercepter(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging
}

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val movieListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(getLoggerIntercepter())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.MOVIE_LIST_BASE_URL)
    .build()

interface TMDBApiService{
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ): MovieResponse

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ):MovieResponse

    @GET("{movie_id}?api_key=${Constants.API_KEY}")
    suspend fun getMovieDetails(
        @Path("movie_id")
        movieId: String
    ): MovieDetailsResponse

    @GET("{movie_id}/reviews?api_key=${Constants.API_KEY}")
    suspend fun getMovieReviews(
        @Path("movie_id")
        movieId: String
    ): MovieReviewsResponse

    @GET("{movie_id}/videos?api_key=${Constants.API_KEY}")
    suspend fun getMovieVideos(
        @Path("movie_id")
        movieId: String
    ): MovieVideosResponse
}

object TMDBApi{
    val movieListRetrofitService: TMDBApiService by lazy { movieListRetrofit.create(TMDBApiService::class.java) }
}