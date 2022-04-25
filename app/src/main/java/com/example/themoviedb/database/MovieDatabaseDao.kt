package com.example.themoviedb.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.themoviedb.model.Movie

@Dao
interface MovieDatabaseDao {

    @Insert
    suspend fun  insert(movie : Movie)


    @Delete
    suspend fun delete(movie: Movie)


    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies():List<Movie>


    @Query("SELECT EXISTS(SELECT * from movies WHERE id = :id)")
    suspend fun isFavorite(id:Long):Boolean
}