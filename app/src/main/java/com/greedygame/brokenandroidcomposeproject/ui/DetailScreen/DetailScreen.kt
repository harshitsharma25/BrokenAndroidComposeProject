package com.greedygame.brokenandroidcomposeproject.ui.DetailScreen

import android.content.Intent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.greedygame.brokenandroidcomposeproject.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.greedygame.brokenandroidcomposeproject.model.Article
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun DetailScreen(
    navController: NavHostController,
    article: Article,
) {
    var isLiked by remember { mutableStateOf(false) }
    var isBookmarked by remember { mutableStateOf(false) }
    var showShareDialog by remember { mutableStateOf(false) }

    // Scroll state for parallax effect
    val scrollState = rememberScrollState()
    val imageHeight = 350.dp
    val imageHeightPx = with(LocalDensity.current) { imageHeight.toPx() }
    val parallaxOffset = (scrollState.value * 0.5f).coerceAtMost(imageHeightPx)
    val context = LocalContext.current



    // Share function
    fun shareArticle() {
        val shareText = buildString {
            append("📰 ${article.title}\n\n")
            if (!article.description.isNullOrEmpty()) {
                append("${article.description}\n\n")
            }
            append("Read more: ${article.url}\n\n")
            append("Shared from ${article.source.name}")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_TITLE, article.title)
            type = "text/plain"
        }


        val shareIntent = Intent.createChooser(sendIntent, "Share Article")
        context.startActivity(shareIntent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF000000),
                        Color(0xFF0D0D0D),
                        Color(0xFF000000)
                    )
                )
            )
    ) {
        // Main Content with Scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // Hero Image Section with Parallax
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            ) {
                // Background Image with Parallax
                if (!article.urlToImage.isNullOrEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(article.urlToImage)
                            .crossfade(true)
                            .build(),
                        contentDescription = article.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(imageHeight + 100.dp)
                            .offset { IntOffset(0, -parallaxOffset.toInt()) },
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF9E019E).copy(alpha = 0.6f),
                                        Color(0xFF6650A4).copy(alpha = 0.4f),
                                        Color(0xFF7D5260).copy(alpha = 0.5f)
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.image_48px),
                            contentDescription = "No image",
                            tint = Color.White.copy(alpha = 0.3f),
                            modifier = Modifier.size(120.dp)
                        )
                    }
                }

                // Gradient Overlays for better text visibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.7f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.9f)
                                ),
                                startY = 0f,
                                endY = imageHeightPx
                            )
                        )
                )

                // Source Badge with Animation
                androidx.compose.animation.AnimatedVisibility(
                    visible = scrollState.value < 100,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 20.dp, top = 60.dp),
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFF9E019E).copy(alpha = 0.95f),
                        shadowElevation = 8.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.newspaper_24px),
                                contentDescription = "Source",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = article.source.name,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Content Card with Glass Effect
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-40).dp),
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1A1A1A).copy(alpha = 0.98f),
                                    Color(0xFF0D0D0D).copy(alpha = 0.99f)
                                )
                            )
                        )
                        .border(
                            width = 1.dp,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF9E019E).copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            ),
                            shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {
                        // Title Section with Animation
                        Text(
                            text = article.title,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 32.sp,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .animateContentSize()
                        )

                        // Meta Information Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Author Info
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = Color(0xFF9E019E).copy(alpha = 0.3f),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Author",
                                            tint = Color(0xFF9E019E),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = article.author ?: "Unknown Author",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = formatDetailDate(article.publishedAt),
                                        fontSize = 11.sp,
                                        color = Color(0xFFB1B1B1)
                                    )
                                }
                            }

                            // Action Buttons
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Like Button
                                Surface(
                                    onClick = { isLiked = !isLiked },
                                    shape = CircleShape,
                                    color = if (isLiked) Color(0xFFFF1744).copy(alpha = 0.2f)
                                    else Color(0xFF2A2A2A),
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (isLiked) Icons.Default.Favorite
                                            else Icons.Default.FavoriteBorder,
                                            contentDescription = "Like",
                                            tint = if (isLiked) Color(0xFFFF1744)
                                            else Color(0xFFB1B1B1),
                                            modifier = Modifier
                                                .size(20.dp)
                                                .scale(if (isLiked) 1.2f else 1f)
                                        )
                                    }
                                }

                                // Bookmark Button
                                Surface(
                                    onClick = { isBookmarked = !isBookmarked },
                                    shape = CircleShape,
                                    color = if (isBookmarked) Color(0xFFFFD700).copy(alpha = 0.2f)
                                    else Color(0xFF2A2A2A),
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            painter = if(isBookmarked) painterResource(R.drawable.bookmark_check_24px) else painterResource(R.drawable.bookmark_24px),
                                            contentDescription = "Bookmark",
                                            tint = if (isBookmarked) Color(0xFFFFD700)
                                            else Color(0xFFB1B1B1),
                                            modifier = Modifier
                                                .size(20.dp)
                                                .scale(if (isBookmarked) 1.2f else 1f)
                                        )
                                    }
                                }

                                // Share Button
                                Surface(
                                    onClick = { shareArticle()  },
                                    shape = CircleShape,
                                    color = Color(0xFF2A2A2A),
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share",
                                            tint = Color(0xFFB1B1B1),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }

                        // Decorative Divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .padding(vertical = 8.dp)
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color(0xFF9E019E).copy(alpha = 0.5f),
                                            Color(0xFF6650A4).copy(alpha = 0.5f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Description Section
                        if (!article.description.isNullOrEmpty()) {
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 20.dp),
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFF2A2A2A).copy(alpha = 0.5f)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .width(4.dp)
                                            .height(60.dp)
                                            .background(
                                                brush = Brush.verticalGradient(
                                                    colors = listOf(
                                                        Color(0xFF9E019E),
                                                        Color(0xFF6650A4)
                                                    )
                                                ),
                                                shape = RoundedCornerShape(2.dp)
                                            )
                                    )

                                    Text(
                                        text = article.description,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFFE0E0E0),
                                        lineHeight = 24.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }

                        // Content Section Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.article_24px),
                                contentDescription = "Content",
                                tint = Color(0xFF9E019E),
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Full Article",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(1.dp)
                                    .background(
                                        Color(0xFF9E019E).copy(alpha = 0.3f)
                                    )
                            )
                        }

                        // Full Content
                        if (!article.content.isNullOrEmpty()) {
                            Text(
                                text = article.content,
                                fontSize = 15.sp,
                                color = Color(0xFFD1D5DB),
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        } else {
                            Text(
                                text = "Full content is not available. Visit the source website to read the complete article.",
                                fontSize = 15.sp,
                                color = Color(0xFFB1B1B1),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                lineHeight = 24.sp,
                                modifier = Modifier.padding(bottom = 20.dp)
                            )
                        }

                        // Read Full Article Button
                        Surface(
                            onClick = { /* Open URL in browser */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.Transparent
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color(0xFF9E019E),
                                                Color(0xFF6650A4),
                                                Color(0xFF7D5260)
                                            )
                                        )
                                    )
                                    .padding(18.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Icon(
                                       painter = painterResource(R.drawable.open_in_browser_24px),
                                        contentDescription = "Open",
                                        tint = Color.White,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Text(
                                        text = "Read Full Article on ${article.source.name}",
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Icon(
                                        imageVector = Icons.Default.ArrowForward,
                                        contentDescription = "Arrow",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }

        // Floating Back Button with Glass Effect
        AnimatedVisibility(
            visible = scrollState.value < 200,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally(),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 16.dp, top = 50.dp)
        ) {
            Surface(
                onClick = { navController.popBackStack() },
                shape = CircleShape,
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier.size(48.dp),
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = Color.White.copy(alpha = 0.2f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

}

// Helper function for detailed date formatting
fun formatDetailDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString)
        val outputFormat = SimpleDateFormat("MMMM dd, yyyy • HH:mm", Locale.getDefault())
        outputFormat.format(date ?: Date())
    } catch (e: Exception) {
        "Recently published"
    }
}
