package com.example.themoviedb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.themoviedb.model.Movie
import com.example.themoviedb.model.PopularMovie
import com.example.themoviedb.model.TopRatedMovie

@Database(entities = [Movie::class, PopularMovie::class, TopRatedMovie::class], version = 4, exportSchema = false)
abstract class MovieDatabase :RoomDatabase(){

    abstract fun movieDatabaseDao(): MovieDatabaseDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}