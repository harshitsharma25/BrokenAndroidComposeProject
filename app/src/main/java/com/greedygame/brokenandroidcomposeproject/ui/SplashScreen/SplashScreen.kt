package com.greedygame.brokenandroidcomposeproject.ui.SplashScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.greedygame.brokenandroidcomposeproject.Navigation.Screens
import com.greedygame.brokenandroidcomposeproject.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) } // Initial scale for animation

    // Launch the animation and navigate to the Home Screen after a delay
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500, // Animation duration
                easing = FastOutSlowInEasing // Smooth animation easing
            )
        )
        delay(500) // Pause before navigation
        navController.navigate(Screens.HOME_SCREEN.name) {
            popUpTo(Screens.SPLASH_SCREEN.name) { inclusive = true } // Clear SplashScreen from back stack
        }
    }

    // UI for the Splash Screen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.app_name), // Replace with your app name or logo
            color = Color.Red,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.scale(scale.value) // Apply scale animation
        )
    }
}