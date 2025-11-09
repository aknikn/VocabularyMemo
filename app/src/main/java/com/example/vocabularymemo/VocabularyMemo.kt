package com.example.vocabularymemo

import android.app.Application
import androidx.room.Room

class VocabularyMemo : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "word-database"
        ).build()
    }
}
