package com.greedygame.brokenandroidcomposeproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greedygame.brokenandroidcomposeproject.model.Article

@Database(entities = [Article::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
}