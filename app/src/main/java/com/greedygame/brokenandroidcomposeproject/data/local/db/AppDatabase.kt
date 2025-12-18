package com.greedygame.brokenandroidcomposeproject.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.greedygame.brokenandroidcomposeproject.data.local.dao.ArticleDao
import com.greedygame.brokenandroidcomposeproject.data.local.entity.ArticleEntity
import com.greedygame.brokenandroidcomposeproject.model.Article

@Database(
    entities = [ArticleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao() : ArticleDao
}