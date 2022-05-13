package com.example.themoviedb.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReviewDatabaseDao {

    @Query("select * from reviewdatabaseentity")
    fun getListOfReviews(): LiveData<List<ReviewDatabaseEntity>>

    @Query("select * from reviewdatabaseentity where id IN(:movie_id)")
    fun getReviews(movie_id: String): LiveData<List<ReviewDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( reviews: List<ReviewDatabaseEntity>)

}
@Database(entities = [ReviewDatabaseEntity::class], version = 2, exportSchema = false)
abstract class ReviewsDatabase: RoomDatabase() {
    abstract val reviewDao: ReviewDatabaseDao

}

private lateinit var INSTANCE: ReviewsDatabase

fun getReviewDatabase(context: Context): ReviewsDatabase {
    synchronized(ReviewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            ReviewsDatabase::class.java,
            "reviews").fallbackToDestructiveMigration().build()
        }
    }
    return INSTANCE
}