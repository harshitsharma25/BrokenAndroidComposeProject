package com.greedygame.brokenandroidcomposeproject.ui.NewScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.articles.collectAsState()

    // Trigger data load once
    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }

    when (uiState) {

        is NewScreenUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is NewScreenUiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Something went wrong")
            }
        }

        is NewScreenUiState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No articles found")
            }
        }

        is NewScreenUiState.NewScreenData -> {
            val articles =
                (uiState as NewScreenUiState.NewScreenData).article

            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(
                    items = articles,
//                    key = { it.id }
                ) { article ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(article.title)
                        Text(article.author ?: "no author")
                    }
                }
            }
        }

        null -> Unit
    }
}
