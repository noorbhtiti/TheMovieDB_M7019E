package com.example.themoviedb

import android.app.Application
import timber.log.Timber

class TheMovieDatabase: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}