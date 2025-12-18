package com.greedygame.brokenandroidcomposeproject.Navigation

import android.net.Uri
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greedygame.brokenandroidcomposeproject.ui.DetailScreen.DetailScreen
import com.greedygame.brokenandroidcomposeproject.ui.HomeScreen.HomeScreen
import com.greedygame.brokenandroidcomposeproject.ui.HomeScreen.HomeScreenViewModel
import com.greedygame.brokenandroidcomposeproject.ui.HomeScreen.NewScreenUiState
import com.greedygame.brokenandroidcomposeproject.ui.SplashScreen.SplashScreen


@Composable
fun ArticlesNavigation(colorScheme: ColorScheme,viewModel : HomeScreenViewModel =  hiltViewModel()){

    val navController : NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.SPLASH_SCREEN.name
    ) {
        composable(route = Screens.SPLASH_SCREEN.name) {
            SplashScreen(navController)
        }

//        composable(
//            route = "${JarScreens.VideoPlayerScreen.name}/{videoId}/{videoTitle}",
//            arguments = listOf(
//                navArgument("videoId") { type = NavType.StringType },
//                navArgument("videoTitle") { type = NavType.StringType },
//            )
//        ) { backStackEntry ->
//            val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
//            val videoTitle = backStackEntry.arguments?.getString("videoTitle") ?: "Video"
//            VideoPlayerScreen(videoId, videoTitle, navController)
//        }

        composable(route = Screens.HOME_SCREEN.name) {
            HomeScreen(navController = navController,colorScheme = colorScheme)
        }

        composable(route = Screens.DETAILS_SCREEN.name) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Screens.HOME_SCREEN.name)
            }
            val sharedViewModel: HomeScreenViewModel = hiltViewModel(parentEntry)

            val article = sharedViewModel.selectedArticle.collectAsState().value

            article?.let {
                DetailScreen(article = it, navController = navController)
            }
        }

//        composable(
//            route = "${Screens.DETAILS_SCREEN.name}/{articleId}",
//            arguments = listOf(
//                navArgument("articleId") { type = NavType.StringType }
//            )
//        ) { backStackEntry ->
//
//            val encodedId = backStackEntry.arguments?.getString("articleId")
//            val url = Uri.decode(encodedId)
//
//            val uiState = viewModel.articles.collectAsState().value
//
//            val article = if (uiState is NewScreenUiState.NewScreenData) {
//                uiState.article.find { it.url == url }
//            } else null
//            println("==> debug article : => article: $article, \n uiState : $uiState")
//
//            article?.let {
//                DetailScreen(article = it, navController = navController)
//            }
//        }

//        fun getMovieById(id: Int): Release? {
//            return _movies.value?.find { it.id == id }
//        }
//        composable(route = JarScreens.HomeScreen.name) {
//            HomeScreen(navController)
//        }
//
//        composable(route = JarScreens.ProfileScreen.name) {
//            ProfileScreen(navController)
//        }
//
//        composable(route = JarScreens.NekScreen.name) {
//            NekScreen(navController)
//        }
//
//        composable(route = JarScreens.TransactionScreen.name) {
//            TransactionsScreen(navController)
//        }
//        composable(route = JarScreens.InvestScreen.name) {
//            InvestScreen(navController)
//        }
//        composable(route = JarScreens.GoldPriceTrackerScreen.name) {
//            GoldPriceTrackerScreen(navController)
//        }

    }
}