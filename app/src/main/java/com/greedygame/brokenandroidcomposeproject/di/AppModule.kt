package com.greedygame.brokenandroidcomposeproject.di

import android.content.Context
import androidx.room.Room
import com.greedygame.brokenandroidcomposeproject.data.local.dao.ArticleDao
import com.greedygame.brokenandroidcomposeproject.data.local.db.AppDatabase
import com.greedygame.brokenandroidcomposeproject.data.remote.api.ApiService
import com.greedygame.brokenandroidcomposeproject.data.remote.network.ApiClient
import com.greedygame.brokenandroidcomposeproject.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "articles.db"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideArticleDao(db : AppDatabase) : ArticleDao = db.articleDao()

    @Provides
    @Singleton
    fun provideApiClient() : ApiClient = ApiClient

    @Provides
    @Singleton
    fun provideApiService(apiClient: ApiClient) : ApiService = apiClient.api

    @Provides
    @Singleton
    fun provideArticleRepository(
        api : ApiService,
        dao : ArticleDao
    ) : ArticleRepository = ArticleRepository(api,dao)




}