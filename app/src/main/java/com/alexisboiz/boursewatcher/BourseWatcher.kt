package com.alexisboiz.boursewatcher

import android.app.Application
import android.preference.PreferenceManager
import com.alexisboiz.boursewatcher.database.AppDatabase
import com.bumptech.glide.module.AppGlideModule
import com.google.firebase.auth.FirebaseAuth

class BourseWatcher : Application() {
    lateinit var auth: FirebaseAuth
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        auth = FirebaseAuth.getInstance()
    }


}