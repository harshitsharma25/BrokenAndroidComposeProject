package com.greedygame.brokenandroidcomposeproject.repository


import com.greedygame.brokenandroidcomposeproject.data.remote.network.ApiClient
import com.greedygame.brokenandroidcomposeproject.model.Article

class ArticleRepository  {

    suspend fun fetchArticles(): List<Article> {
        val response = ApiClient.api.getArticles(
            query = "android"
//            query = Constants.QUERY_ANDROID
        )
        return response.articles
    }

    suspend fun updateArticle(article: Article) {
        // later: API / DB
    }
}
