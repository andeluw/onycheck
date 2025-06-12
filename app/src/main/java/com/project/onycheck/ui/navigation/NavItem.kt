package com.project.onycheck.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MedicalInformation
import androidx.compose.material.icons.rounded.Newspaper
import androidx.compose.material.icons.rounded.SavedSearch
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class NavItem(
    val route: String,
    val icon: ImageVector? = null,
    val title: String? = null
) {
    object Home : NavItem("home", Icons.Rounded.Home, "Home")
    object Analyze : NavItem("analyze", Icons.Rounded.SavedSearch, "Analyze")
    object News : NavItem("news", Icons.Rounded.Newspaper, "News")
    object Doctors : NavItem("doctors", Icons.Rounded.MedicalInformation, "Doctors")

    object DiseaseDetail : NavItem("disease_detail")

    fun navigate(navController: NavController) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun createDiseaseDetailRoute(diseaseName: String): String {
    return "${NavItem.DiseaseDetail.route}/$diseaseName"
}