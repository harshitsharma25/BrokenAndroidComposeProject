package com.greedygame.brokenandroidcomposeproject.ui.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.greedygame.brokenandroidcomposeproject.R
import com.greedygame.brokenandroidcomposeproject.model.Article
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.res.painterResource
import com.greedygame.brokenandroidcomposeproject.Navigation.Screens
import com.greedygame.brokenandroidcomposeproject.utils.Utility.Companion.formatDate
import android.content.Intent

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    colorScheme: ColorScheme
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
                ShimmerView(modifier)
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

            ArticleScreen(articles,modifier, navController = navController,viewModel)
        }

        null -> Unit
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    articles: List<Article>,
    modifier: Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel
) {
    val context = LocalContext.current
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.my_articles)) }) }
    ){ innerPadding ->

        LazyColumn(modifier = modifier.fillMaxSize().padding(innerPadding)) {
            items(
                items = articles,
            ) { article ->
                ArticleCard(
                    article,
                    onReadMoreClicked = {
//                        val articleId = article.url.hashCode()      // <-- safe Int id
//                        navController.navigate("${Screens.DETAILS_SCREEN.name}/$articleId")
//                        val encoded = Uri.encode(article.url)
//                        navController.navigate("${Screens.DETAILS_SCREEN.name}/$encoded")

                        viewModel.selectArticle(article)
                        navController.navigate(Screens.DETAILS_SCREEN.name)

                    },
                    onShareClicked = {
                        // Share function
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
                )
            }
        }

    }
}

//@Composable
//fun ArticleCard(article: Article) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(0.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(dimensionResource(R.dimen.shimmer_card_height))
//        ){
//
//        }
//    }
//}

@Composable
fun ArticleCard(article: Article, onReadMoreClicked: () -> Unit , onShareClicked : () -> Unit) {
    var isLiked by remember { mutableStateOf(false) }
    var isEdited by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(dimensionResource(R.dimen.shimmer_card_height)),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A1A1A).copy(alpha = 0.95f),
                            Color(0xFF0D0D0D).copy(alpha = 0.98f)
                        )
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF9E019E).copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Left Side - Image with Glass Effect Overlay
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .fillMaxHeight()
                ) {
                    // Article Image
                    if (!article.urlToImage.isNullOrEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(article.urlToImage)
                                .crossfade(true)
                                .build(),
                            contentDescription = article.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)),
                            contentScale = ContentScale.Crop
                        )

                        // Glass Effect Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color(0xFF000000).copy(alpha = 0.7f)
                                        )
                                    )
                                )
                        )
                    } else {
                        // Placeholder with gradient
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF9E019E).copy(alpha = 0.3f),
                                            Color(0xFF6650A4).copy(alpha = 0.3f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.image_48px),
                                contentDescription = "No image",
                                tint = Color(0xFFB1B1B1).copy(alpha = 0.5f),
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }

                    // Source Badge with Glass Effect
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF9E019E).copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = article.source.name,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Right Side - Content with Glass Effect
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF1F1F1F).copy(alpha = 0.6f),
                                    Color(0xFF0A0A0A).copy(alpha = 0.8f)
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Title and Description
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Top
                        ) {
                            // Title with gradient text effect
                            Text(
                                text = article.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(bottom = 6.dp)
                            )

                            // Description
                            if (!article.description.isNullOrEmpty()) {
                                Text(
                                    text = article.description,
                                    fontSize = 12.sp,
                                    color = Color(0xFFB1B1B1),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    lineHeight = 15.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Bottom Section - Meta Info and Actions
                        Column {
                            // Author and Date Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Author
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    modifier = Modifier.weight(1f, fill = false)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Author",
                                        tint = Color(0xFF9E019E).copy(alpha = 0.7f),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = article.author ?: "Unknown",
                                        fontSize = 10.sp,
                                        color = Color(0xFFB1B1B1),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }

                                // Date
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                       painter = painterResource(R.drawable.schedule_24px),
                                        contentDescription = "Date",
                                        tint = Color(0xFF9E019E).copy(alpha = 0.7f),
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = formatDate(article.publishedAt),
                                        fontSize = 10.sp,
                                        color = Color(0xFFB1B1B1)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Divider with gradient
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .
                                    background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color(0xFF9E019E).copy(alpha = 0.3f),
                                                Color.Transparent
                                            )
                                        )
                                    )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Action Buttons Row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Like and Bookmark Buttons
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    // Like Button
                                    IconButton(
                                        onClick = { isLiked = !isLiked },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                            contentDescription = "Like",
                                            tint = if (isLiked) Color(0xFFFF1744) else Color(0xFFB1B1B1),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Bookmark Button
                                    IconButton(
                                        onClick = { isEdited = !isEdited },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
//                                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                            painter = if(isEdited) painterResource(R.drawable.bookmark_check_24px) else painterResource(R.drawable.bookmark_24px),
                                            contentDescription = "Bookmark",
                                            tint = if (isEdited) Color(0xFFFFD700) else Color(0xFFB1B1B1),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    // Share Button
                                    IconButton(
                                        onClick = {
                                            onShareClicked()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = "Share",
                                            tint = Color(0xFFB1B1B1),
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }

                                // Read More Button with Glass Effect
                                Surface(
                                    onClick = {
                                        onReadMoreClicked()
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color(0xFF9E019E).copy(alpha = 0.8f),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxHeight().padding(horizontal = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        Text(
                                            text = "Read",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color.White,
                                            modifier = Modifier.offset(y = (-0.5).dp)
                                        )
                                        Icon(
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = "Arrow",
                                            tint = Color.White,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Helper function to format date


@Composable
fun ShimmerView(modifier: Modifier){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = dimensionResource(R.dimen.padding_16_dp),
                end = dimensionResource(R.dimen.padding_16_dp),
                top = dimensionResource(R.dimen.padding_48_dp),
                bottom = dimensionResource(R.dimen.padding_16_dp),
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_16_dp))
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .placeholder(
                        visible = true,
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
        }
        repeat(3){
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.shimmer_card_height))
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
            }
        }
    }
}
