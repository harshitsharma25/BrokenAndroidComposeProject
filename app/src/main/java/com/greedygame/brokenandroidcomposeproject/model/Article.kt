package com.greedygame.brokenandroidcomposeproject.model

import com.google.gson.annotations.SerializedName
import com.greedygame.brokenandroidcomposeproject.data.local.entity.ArticleEntity


data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String
)

fun ArticleEntity.toArticle() : Article{
    return Article(
        url = url,
        author = author,
        title = title,
        description = description,
        urlToImage =  urlToImage,
        publishedAt = publishedAt,
        content = content,
        source = Source(sourceId,sourceName)
    )
}

fun Article.toArticleEntity() : ArticleEntity {
    return ArticleEntity(
        url = url,
        author = author,
        title = title,
        description = description,
        urlToImage =  urlToImage,
        publishedAt = publishedAt,
        content = content,
        sourceName = source.name,
        sourceId = source.id,
        cachedAt = System.currentTimeMillis()
    )
}
