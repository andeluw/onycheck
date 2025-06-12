package com.project.onycheck.ui.screens.diseasedetail

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.onycheck.data.DiseaseData
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.navigation.NavItem
import com.project.onycheck.ui.theme.Blue700
import com.project.onycheck.ui.theme.Blue800
import com.project.onycheck.ui.theme.Gray500
import com.project.onycheck.ui.theme.Gray700
import com.project.onycheck.ui.theme.Gray900
import com.project.onycheck.ui.theme.Red700

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetailScreen(diseaseName: String, navController: NavController) {
    val detail = DiseaseData.findDisease(diseaseName)

    AppScaffold(
        navController = navController,
        topBar = {
            TopAppBar(
                title = { /* No title here */ },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Gray900
                        )
                    }
                },
                modifier = Modifier.background(Color.White)
            )
        }
    ) { innerPadding ->
        if (detail == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("Disease details not found.", style = MaterialTheme.typography.bodyLarge)
            }
            return@AppScaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 24.dp)
        ) {
            item {
                Text(
                    text = detail.userFriendlyName,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = Gray900
                )
            }

            // 1. Image
            item {
                Image(
                    painter = painterResource(id = detail.imageRes),
                    contentDescription = detail.userFriendlyName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // 2. Overview
            item {
                DetailSection(title = "Overview", content = detail.overview)
            }

            // 3. Symptoms
            item {
                DetailSection(title = "Symptoms & Signs", items = detail.symptoms)
            }

            // 4. Causes
            item {
                DetailSection(title = "Causes & Risk Factors", items = detail.causes)
            }

            // 5. What to Do Next
            item {
                DetailSection(title = "What to Do Next", items = detail.nextSteps, isWarning = true)
            }

            // 6. CTA Buttons
            item {
                Column(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = { NavItem.Doctors.navigate(navController) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue700,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            "Find a Doctor Nearby",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    TextButton(
                        onClick = { NavItem.Home.navigate(navController) },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Blue800
                        )
                    ) {
                        Text(
                            "Back to Home",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // 7. Disclaimer
            item {
                Text(
                    text = "This analysis is powered by AI and is not a substitute for professional medical advice. Always consult with a licensed healthcare provider.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray500,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp, top = 12.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: String? = null,
    items: List<String>? = null,
    isWarning: Boolean = false
) {
    Column(modifier = Modifier.padding(bottom = 24.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = if (isWarning) Red700 else Gray900
        )
        Spacer(modifier = Modifier.height(8.dp))
        content?.let {
            Text(text = it, style = MaterialTheme.typography.bodyLarge, color = Gray900)
        }
        items?.forEach { item ->
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text("•", modifier = Modifier.padding(end = 8.dp), color = Gray700)
                Text(text = item, style = MaterialTheme.typography.bodyLarge, color = Gray900)
            }
        }
    }
}