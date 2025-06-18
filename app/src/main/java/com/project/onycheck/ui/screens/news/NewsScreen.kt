package com.project.onycheck.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.onycheck.data.NewsArticle
import com.project.onycheck.data.NewsData
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.navigation.createNewsArticleDetailRoute
import com.project.onycheck.ui.theme.Blue50
import com.project.onycheck.ui.theme.Gray600
import com.project.onycheck.ui.theme.Gray700
import com.project.onycheck.ui.theme.Gray900

@Composable
fun NewsScreen(navController: NavController) {
    AppScaffold(navController = navController) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp),
//            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "Health News",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Gray900
                )
            }
            items(NewsData.allNews) { article ->
                NewsArticleCard(
                    article = article,
                    onClick = {
                        navController.navigate(createNewsArticleDetailRoute(article.id))
                    }
                )
            }
        }
    }
}

@Composable
private fun NewsArticleCard(article: NewsArticle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Blue50)
    ) {
        Row(modifier = Modifier.height(120.dp)) {
            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "By ${article.author}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray700
                )
            }
        }
    }
}