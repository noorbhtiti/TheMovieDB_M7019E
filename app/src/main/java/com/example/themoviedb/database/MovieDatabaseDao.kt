package com.example.themoviedb.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.themoviedb.model.Movie

@Dao
interface MovieDatabaseDao {

    @Insert
    suspend fun insert(movie : Movie)


    @Delete
    suspend fun delete(movie: Movie)


    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( movies: List<Movie>)

    @Query("SELECT EXISTS(SELECT * from movies WHERE id = :id)")
    suspend fun isFavorite(id:Long):Boolean
}
@Dao
interface MovieCacheDao {

    @Insert
    suspend fun insert(movie : Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movies ORDER BY id ASC")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * from movies ORDER BY id ASC")
    fun getMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll( movies: List<Movie>)

}
