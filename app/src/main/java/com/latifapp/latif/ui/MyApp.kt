package com.latifapp.latif.ui

import android.app.Application
import com.cloudinary.android.MediaManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp :Application() {
    override fun onCreate() {
        super.onCreate()
        MediaManager.init(this)
    }
}