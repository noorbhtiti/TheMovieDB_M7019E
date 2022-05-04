package com.example.themoviedb.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Room.*

@Dao
interface ReviewDatabaseDao {

    @Query("select * from reviewdatabase")
    fun getListOfReviews(): LiveData<List<ReviewDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<ReviewDatabase>)

}
@Database(entities = [ReviewDatabase::class], version = 1)
abstract class ReviewsDatabase: RoomDatabase() {
    abstract val reviewDao: ReviewDatabaseDao

}

private lateinit var INSTANCE: ReviewsDatabase

fun getDatabase(context: Context): ReviewsDatabase {
    synchronized(ReviewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            ReviewsDatabase::class.java,
            "reviews").build()
        }
    }
    return INSTANCE
}