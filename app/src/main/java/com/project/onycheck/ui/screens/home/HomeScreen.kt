package com.project.onycheck.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.MedicalInformation
import androidx.compose.material.icons.rounded.SavedSearch
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.onycheck.data.Doctor
import com.project.onycheck.data.DoctorData
import com.project.onycheck.data.NewsArticle
import com.project.onycheck.data.NewsData
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.navigation.NavItem
import com.project.onycheck.ui.navigation.createNewsArticleDetailRoute
import com.project.onycheck.ui.theme.Blue100
import com.project.onycheck.ui.theme.Blue200
import com.project.onycheck.ui.theme.Blue300
import com.project.onycheck.ui.theme.Blue400
import com.project.onycheck.ui.theme.Blue50
import com.project.onycheck.ui.theme.Blue700
import com.project.onycheck.ui.theme.Blue800
import com.project.onycheck.ui.theme.Blue900
import com.project.onycheck.ui.theme.Gray50
import com.project.onycheck.ui.theme.Gray700
import com.project.onycheck.ui.theme.Gray900

@Composable
fun HomeScreen(navController: NavController) {
    AppScaffold(navController = navController) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .padding(top = 48.dp),
//            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // --- Welcome Header ---
            item {
                Column(
//                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        "Welcome to OnyCheck",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Your Personal Nail Health Assistant",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            item {
                MainActionCard(
                    title = "AI Nail Analysis",
                    description = "Use your camera to get an instant analysis of your nail health.",
                    icon = Icons.Rounded.SavedSearch,
                    onClick = { NavItem.Analyze.navigate(navController) },
                    modifier = Modifier.padding(top = 24.dp, bottom = 24.dp)
                )
            }

            item {
                ContentSection(
                    title = "Health News",
                    onViewMore = { NavItem.News.navigate(navController) }
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 24.dp)
//                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(NewsData.allNews.take(4)) { article ->
                            NewsPreviewCard(
                                article = article,
                                onClick = {
                                    navController.navigate(createNewsArticleDetailRoute(article.id))
                                }
                            )
                        }
                    }
                }
            }
            item {
                ContentSection(
                    title = "Nearby Doctors",
                    onViewMore = { NavItem.Doctors.navigate(navController) }
                ) {
                    Column(
                        modifier = Modifier.padding(bottom = 48.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        DoctorData.allDoctors.take(3).forEach { doctor ->
                            DoctorPreviewCard(doctor = doctor,
                                onClick = { NavItem.Doctors.navigate(navController) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentSection(
    title: String,
    onViewMore: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
            TextButton(onClick = onViewMore) {
                Text("View More", color = Blue800)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        content()
    }
}


@Composable
private fun NewsPreviewCard(article: NewsArticle, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Gray50)
    ) {
        Column {
            Image(
                painter = painterResource(id = article.imageRes),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray700
                )
            }
        }
    }
}


@Composable
private fun DoctorPreviewCard(doctor: Doctor, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blue50)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(0.5.dp, Blue800)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.MedicalInformation,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .background(Blue300, CircleShape)
                    .padding(8.dp),
                tint = Blue800
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Gray900
                )

                if (doctor.rating > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = Color(0xFFF59E0B),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${doctor.rating} (${doctor.totalRatings} reviews)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = Gray700
                        )
                    }
                } else {
                    Text(
                        text = "View Details",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Gray700
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = "Go to details",
                tint = Blue800,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun MainActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Blue200.copy(alpha = 0.8f),
            Blue50.copy(alpha = 0.6f)
        )
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Blue50
        )
    ) {
        Box(
            modifier = Modifier.background(gradientBrush)
        ) {
            Column(
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 20.dp
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Blue700
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gray900
                )
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    FeatureItem(icon = Icons.Rounded.CheckCircle, text = "4 Conditions")
                    FeatureItem(icon = Icons.Rounded.Speed, text = "Instant Results")
                }

                Spacer(modifier = Modifier.height(24.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Start Your Analysis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Blue700
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Start Analysis",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(20.dp),
                        tint = Blue700
                    )
                }
            }
        }
    }
}

@Composable
private fun FeatureItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Blue700,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            color = Gray700
        )
    }
}
