package com.greedygame.brokenandroidcomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.greedygame.brokenandroidcomposeproject.ui.NewScreen.NewsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var leakedActivity: MainActivity? = null
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leakedActivity = this

        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Scaffold(topBar = { TopAppBar(title = { Text("Broken News") }) }) { padding ->
                    NewsScreen(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}