package com.project.onycheck.ui.screens.analyze.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.project.onycheck.R
import com.project.onycheck.data.remote.dto.PredictionResponse
import com.project.onycheck.ui.components.AnimatedCirclesLoader
import com.project.onycheck.ui.screens.analyze.AnalyzeUiState
import com.project.onycheck.ui.theme.Blue700
import com.project.onycheck.ui.theme.Blue800
import com.project.onycheck.ui.theme.Gray200
import com.project.onycheck.ui.theme.Gray300
import com.project.onycheck.ui.theme.Gray50
import com.project.onycheck.ui.theme.Gray500
import com.project.onycheck.ui.theme.Gray700
import com.project.onycheck.ui.theme.Gray800
import com.project.onycheck.ui.theme.Gray900
import com.project.onycheck.ui.theme.Red700
import java.util.Locale

@Composable
internal fun DefaultAnalyzeContent(
    innerPadding: PaddingValues,
    onAnalyzeClick: () -> Unit,
    onShowGuide: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
            .padding(36.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Smart AI Nail Analysis",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            color = Gray900,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Get instant insights into your nail health. Our AI detects conditions like:",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Gray700
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Melanoma, Onychogryphosis, Clubbing, Pitting, and Blue Finger.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = Gray800
        )
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.people_check),
            contentDescription = "Nail analysis illustration",
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
        )

//        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onAnalyzeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue700,
                contentColor = Color.White
            )
        ) {
            Text(
                "Analyze Your Nail",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onShowGuide,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Blue800
            )
        ) {
            Text(
                "How to Take a Good Photo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
internal fun LoadingView(uiState: AnalyzeUiState, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.selectedImageUri != null) {
            AsyncImage(
                model = uiState.selectedImageUri,
                contentDescription = "Analyzing nail image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Gray300)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        AnimatedCirclesLoader(modifier = Modifier.size(56.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Analyzing your image...",
            style = MaterialTheme.typography.titleMedium,
            color = Gray900
        )
    }
}

@Composable
internal fun ResultView(
    uiState: AnalyzeUiState,
    innerPadding: PaddingValues,
    onAnalyzeAnother: () -> Unit,
    onShowGuide: () -> Unit,
    onLearnMore: (String) -> Unit,
    onFindDoctor: () -> Unit,
    onGoHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
            .padding(horizontal = 36.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Spacer(modifier = Modifier.weight(1f))

        if (uiState.selectedImageUri != null) {
            AsyncImage(
                model = uiState.selectedImageUri,
                contentDescription = "Analyzed nail image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Gray300)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        val result = uiState.predictionResult

//        Spacer(modifier = Modifier.weight(1f))

        Box(contentAlignment = Alignment.Center) {
            if (uiState.error != null || result == null || result.predicted_class.isNullOrBlank() || result.predicted_class == "Unknown") {
                ErrorResultContent(onTryAgain = onAnalyzeAnother, onShowGuide = onShowGuide)
            } else if (result.predicted_class == "Healthy_Nail") {
                HealthyResultContent(onAnalyzeAnother = onAnalyzeAnother, onGoHome = onGoHome)
            } else {
                DiseaseResultContent(
                    result = result,
                    onLearnMore = onLearnMore,
                    onFindDoctor = onFindDoctor,
                    onAnalyzeAnother = onAnalyzeAnother
                )
            }
        }
    }
}

@Composable
private fun ErrorResultContent(onTryAgain: () -> Unit, onShowGuide: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Error",
            tint = Red700,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Analysis Failed",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "We couldn't analyze your photo. Please ensure your nail is clear, well-lit, and try again.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Gray800
        )
//        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onTryAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue700,
                contentColor = Color.White
            )
        ) {
            Text(
                "Try Again",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        TextButton(
            onClick = onShowGuide,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Blue800
            )
        ) {
            Text(
                "View Photo Guide",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun HealthyResultContent(onAnalyzeAnother: () -> Unit, onGoHome: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = "Healthy",
            tint = Color(0xFF16A34A),
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Great News!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Our analysis suggests your nail is healthy. Keep up the great nail care!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Gray700
        )
//        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onAnalyzeAnother,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue700,
                contentColor = Color.White
            )
        ) {
            Text(
                "Analyze Another Nail",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        TextButton(
            onClick = onGoHome,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Blue800
            )
        ) {
            Text(
                "Done",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DiseaseResultContent(
    result: PredictionResponse,
    onLearnMore: (String) -> Unit,
    onFindDoctor: () -> Unit,
    onAnalyzeAnother: () -> Unit
) {
    val diseaseNameMapping = mapOf(
        "Acral_Lentiginous_Melanoma" to "Acral Lentiginous Melanoma",
        "Onychogryphosis" to "Onychogryphosis",
        "blue_finger" to "Blue Finger",
        "clubbing" to "Nail Clubbing",
        "pitting" to "Nail Pitting"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Rounded.Warning,
            contentDescription = "Error",
            tint = Red700,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Potential Condition Detected!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Gray900
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = diseaseNameMapping[result.predicted_class]
                ?: result.predicted_class
                    ?.replace("_", " ")
                    ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                ?: "Unknown",
                style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Red700,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confidence: ${"%.1f".format(result.confidence * 100)}%",
            style = MaterialTheme.typography.titleMedium,
            color = Gray700
        )
//        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onLearnMore(result.predicted_class) },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue700,
                contentColor = Color.White
            )
        ) {
            Text(
                "Learn More",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onFindDoctor,
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Blue700
            ),
            border = BorderStroke(1.5.dp, Blue700)
        ) {
            Text(
                "Find a Doctor",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(
            onClick = onAnalyzeAnother,
            colors = ButtonDefaults.textButtonColors(
                contentColor = Blue800
            )
        ) {
            Text(
                "Analyze a Different Nail",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Disclaimer: This AI analysis is for informational purposes only and is not a substitute for a professional medical diagnosis.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            color = Gray500
        )
    }

}

@Composable
internal fun PhotoGuideDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Photo Guide", style = MaterialTheme.typography.titleLarge, color = Gray900, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.correctnail),
                        contentDescription = "Good photo example",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color(0xFF16A34A), CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        "Good",
                        color = Color(0xFF16A34A),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.wrongnails),
                        contentDescription = "Bad photo example",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .border(2.dp, Red700, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        "Bad",
                        color = Red700,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ensure your nail is in focus, well-lit, and fills the frame.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = Gray900
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue800
                )
            ) {
                Text(
                    "Got It",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}