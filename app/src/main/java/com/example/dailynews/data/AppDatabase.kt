package com.example.dailynews.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dailynews.model.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
