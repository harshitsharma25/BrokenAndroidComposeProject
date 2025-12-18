package com.greedygame.brokenandroidcomposeproject.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.greedygame.brokenandroidcomposeproject.model.Source


@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,

    val sourceId: String?,
    val sourceName: String,

    val author: String?,
    val title: String,
    val description: String?,

    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    val cachedAt : Long
)
