package com.project.onycheck.ui.screens.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.onycheck.data.NewsData
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.theme.Gray700
import com.project.onycheck.ui.theme.Gray900

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsArticleDetailScreen(articleId: Int, navController: NavController) {
    val article = NewsData.findArticle(articleId)

    AppScaffold(
        navController = navController,
        topBar = {
            TopAppBar(
                title = { /* No title here */ },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Gray900
                        )
                    }
                },
                modifier = Modifier.background(Color.White)
            )
        }
    ) { innerPadding ->
        if (article == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Article not found.")
            }
            return@AppScaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            // Title and Author
            item {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "By ${article.author} • ${article.date}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Gray700
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            // Image
            item {
                Image(
                    painter = painterResource(id = article.imageRes),
                    contentDescription = article.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
            // Content
            item {
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.5,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}