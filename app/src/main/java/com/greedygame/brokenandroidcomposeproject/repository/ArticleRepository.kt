package com.greedygame.brokenandroidcomposeproject.repository

import com.google.gson.Gson
import com.greedygame.brokenandroidcomposeproject.R
import com.greedygame.brokenandroidcomposeproject.data.remote.network.ApiClient
import com.greedygame.brokenandroidcomposeproject.model.Article
import com.greedygame.brokenandroidcomposeproject.utils.Constants

//
//object BrokenRepository {
//    fun fetchArticlesBlocking(): List<Article> {
////        Thread.sleep(2000)
//        val fakeJson = "[{\"identifier\":1,\"heading\":\"Hello\",\"writer\":\"Alice\"}]"
//        val gson = Gson()
//        val articles: Array<Article> = try {
//            gson.fromJson(fakeJson, Array<Article>::class.java)
//        } catch (e: Exception) {
//            emptyArray()
//        }
//        return articles.toList()
//    }
//
//    fun updateArticle(article: Article) {
//    }
//}

class ArticleRepository  {

//    suspend fun fetchArticles(): List<Article> {
//        val fakeJson = """
//            [
//              {
//                "identifier": 1,
//                "heading": "Hello",
//                "writer": "Alice"
//              }
//            ]
//        """.trimIndent()
//
//        return Gson().fromJson(fakeJson, Array<Article>::class.java).toList()
//    }

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
