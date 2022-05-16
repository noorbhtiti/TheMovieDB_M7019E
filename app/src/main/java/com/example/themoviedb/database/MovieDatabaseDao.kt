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
    suspend fun getPopularMovies(): List<PopularMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopular( popularMovies: List<PopularMovie>)

    @Query("delete from popularMovies")
    suspend fun deletePopular()

    @Query("select * from topRatedMovies")
    suspend fun getTopRatedMovies(): List<TopRatedMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopRated( topRatedMovies:  List<TopRatedMovie>)

    @Query("delete from topRatedMovies")
    suspend fun deleteTopRated()
}