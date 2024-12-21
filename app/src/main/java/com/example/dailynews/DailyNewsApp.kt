package com.example.dailynews

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DailyNewsApp:Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this) // Ensure Firebase is initialized
    }
}