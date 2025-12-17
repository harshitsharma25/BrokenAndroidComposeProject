package com.greedygame.brokenandroidcomposeproject.ui.NewScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedygame.brokenandroidcomposeproject.model.Article
import com.greedygame.brokenandroidcomposeproject.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class NewScreenUiState {
    object Loading : NewScreenUiState()
    object Error   : NewScreenUiState()
    object Empty   : NewScreenUiState()
    data class NewScreenData(val article : List<Article>) : NewScreenUiState()
}


@HiltViewModel
class NewScreenViewModel @Inject constructor(val repository: ArticleRepository) : ViewModel() {
    private val _articles = MutableStateFlow<NewScreenUiState>(NewScreenUiState.Empty)
    val articles: StateFlow<NewScreenUiState> = _articles

    fun loadArticles() {
        _articles.value = NewScreenUiState.Loading
        viewModelScope.launch {
            try {
                _articles.value = NewScreenUiState.NewScreenData(repository.fetchArticles())
            } catch (e : Exception){
                e.printStackTrace()
                _articles.value = NewScreenUiState.Error
            }
        }
    }
}