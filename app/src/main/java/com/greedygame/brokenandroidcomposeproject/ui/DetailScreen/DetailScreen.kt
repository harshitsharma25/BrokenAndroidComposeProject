package com.greedygame.brokenandroidcomposeproject.ui.DetailScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun DetailScreen(articleId: Int) {
    Text(text = "Detail for: $articleId")
}