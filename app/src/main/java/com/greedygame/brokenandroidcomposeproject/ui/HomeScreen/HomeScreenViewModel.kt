package com.greedygame.brokenandroidcomposeproject.ui.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedygame.brokenandroidcomposeproject.data.local.entity.ArticleEntity
import com.greedygame.brokenandroidcomposeproject.model.Article
import com.greedygame.brokenandroidcomposeproject.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NewScreenUiState {
    object Loading : NewScreenUiState()
    object Error   : NewScreenUiState()
    object Empty   : NewScreenUiState()
    data class NewScreenData(val article : List<Article>) : NewScreenUiState()
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(val repository: ArticleRepository) : ViewModel() {
    private var _articles = MutableStateFlow<NewScreenUiState>(NewScreenUiState.Loading)
    val articles: StateFlow<NewScreenUiState> = _articles.asStateFlow()

    init {
        loadArticles()
    }

    fun loadArticles() {
//        viewModelScope.launch {
//            _articles.value = NewScreenUiState.Loading

            // Here i'm collecting Room as 1st (actual meaning of caching)
            viewModelScope.launch {
                repository.articlesFlow.collect { list ->

                    if (list.isEmpty()) {
                        _articles.value = NewScreenUiState.Empty
                    } else {
                        _articles.value = NewScreenUiState.NewScreenData(list)
                    }
                }
            }

        // Here I'm checking the expiration of cache and refresh if needed
        viewModelScope.launch {
            try {
                repository.refreshArticlesIfExpired()

            } catch (e: Exception) {
                e.printStackTrace()
                _articles.value = NewScreenUiState.Error
            }
        }
    }

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle.asStateFlow()

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }
    fun updateArticle(article: Article) {
        viewModelScope.launch {
            repository.updateArticle(article)
        }
    }

}