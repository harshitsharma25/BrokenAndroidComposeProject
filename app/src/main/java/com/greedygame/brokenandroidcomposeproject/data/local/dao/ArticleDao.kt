package com.greedygame.brokenandroidcomposeproject.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.greedygame.brokenandroidcomposeproject.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getArticles() : Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles : List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearArticles()

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Query("SELECT cachedAt FROM articles ORDER BY cachedAt DESC LIMIT 1")
    suspend fun lastCacheTimestamp(): Long?


}