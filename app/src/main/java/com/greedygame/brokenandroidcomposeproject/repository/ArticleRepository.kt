package com.greedygame.brokenandroidcomposeproject.repository


import com.greedygame.brokenandroidcomposeproject.data.local.dao.ArticleDao
import com.greedygame.brokenandroidcomposeproject.data.local.entity.ArticleEntity
import com.greedygame.brokenandroidcomposeproject.data.remote.api.ApiService
import com.greedygame.brokenandroidcomposeproject.data.remote.network.ApiClient
import com.greedygame.brokenandroidcomposeproject.model.Article
import com.greedygame.brokenandroidcomposeproject.model.toArticle
import com.greedygame.brokenandroidcomposeproject.model.toArticleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val api : ApiService,
    private val dao : ArticleDao
){

    val articlesFlow: Flow<List<Article>> =
        dao.getArticles().map { entities ->
            entities.map { it.toArticle() }
        }
    private val EXPIRY_WINDOW = 30 * 60 * 1000L // 30 min

    suspend fun refreshArticles(){
        val now = System.currentTimeMillis()
        val response = api.getArticles("android")

        val entities = response.articles.map { article ->
            ArticleEntity(
                url = article.url,
                sourceId = article.source.id,
                sourceName = article.source.name,
                author = article.author,
                title = article.title,
                description = article.description,
                urlToImage = article.urlToImage,
                publishedAt = article.publishedAt,
                content = article.content,
                cachedAt = now
            )
        }

//        dao.clearArticles()
        dao.insertArticles(entities)
    }

    suspend fun refreshArticlesIfExpired() {

        val lastCacheTime = dao.lastCacheTimestamp()

        val now = System.currentTimeMillis()

        val isExpired =
            lastCacheTime == null || (now - lastCacheTime) > EXPIRY_WINDOW

        if (isExpired) {
            refreshArticles()   // API → DB
        }
    }


    suspend fun updateArticle(article: Article) {
        dao.updateArticle(article.toArticleEntity())
    }


    // at first using this function and populating UI from the api response.
    suspend fun fetchArticles(): List<Article> {
        val response = ApiClient.api.getArticles(
            query = "android"
        )
        return response.articles
    }


}
