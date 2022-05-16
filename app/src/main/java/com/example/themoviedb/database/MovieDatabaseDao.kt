package com.example.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.PopularMovie
import com.example.themoviedb.model.TopRatedMovie

@Dao
interface MovieDatabaseDao {

    @Insert
    suspend fun insert(movie : Movie)


    @Delete
    suspend fun delete(movie: Movie)


    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies():List<Movie>


    @Query("SELECT EXISTS(SELECT * from movies WHERE id = :id)")
    suspend fun isFavorite(id:Long):Boolean

    @Query("select * from popularMovies")
    fun getPopularMovies(): LiveData<List<PopularMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPopular( popularMovies: List<PopularMovie>)

    @Query("select * from topRatedMovies")
    fun getTopRatedMovies(): LiveData<List<TopRatedMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTopRated( topRatedMovies:  List<TopRatedMovie>)
}