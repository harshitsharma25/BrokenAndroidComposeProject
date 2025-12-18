package com.greedygame.brokenandroidcomposeproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.greedygame.brokenandroidcomposeproject.Navigation.ArticlesNavigation
import com.greedygame.brokenandroidcomposeproject.ui.HomeScreen.HomeScreen
import com.greedygame.brokenandroidcomposeproject.ui.theme.ArticlesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        var leakedActivity: MainActivity? = null
    }


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        leakedActivity = this

        setContent {
            ArticlesAppTheme {
                ArticlesAppApp(MaterialTheme.colorScheme)
            }
//            Surface(modifier = Modifier.fillMaxSize()) {
//                Scaffold(topBar = { TopAppBar(title = { Text("Broken News") }) }) { padding ->
//                    ArticlesNavigation(modifier = Modifier.padding(padding))
//                }
//            }
        }
    }

    @Composable
    fun ArticlesAppApp(colorscheme: ColorScheme) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
//            topBar = { TopAppBar(title = { Text("Broken News") }) }
            ){

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                ArticlesNavigation(colorscheme)
            }
        }
    }
}