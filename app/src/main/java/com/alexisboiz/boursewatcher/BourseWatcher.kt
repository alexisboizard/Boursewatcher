package com.alexisboiz.boursewatcher

import android.app.Application
import com.alexisboiz.boursewatcher.database.AppDatabase

class BourseWatcher : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}