package com.project.onycheck.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.onycheck.ui.screens.analyze.AnalyzeScreen
import com.project.onycheck.ui.screens.diseasedetail.DiseaseDetailScreen
import com.project.onycheck.ui.screens.doctors.DoctorsScreen
import com.project.onycheck.ui.screens.home.HomeScreen
import com.project.onycheck.ui.screens.news.NewsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavItem.Analyze.route,
    ) {
        composable(NavItem.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(NavItem.Analyze.route) {
            AnalyzeScreen(navController = navController)
        }
        composable(NavItem.News.route) {
            NewsScreen(navController = navController)
        }
        composable(NavItem.Doctors.route) {
            DoctorsScreen(navController = navController)
        }

        composable(
            route = "${NavItem.DiseaseDetail.route}/{diseaseName}",
            arguments = listOf(navArgument("diseaseName") { type = NavType.StringType })
        ) { backStackEntry ->
            val diseaseName = backStackEntry.arguments?.getString("diseaseName")
            if (diseaseName != null) {
                DiseaseDetailScreen(diseaseName = diseaseName, navController = navController)
            } else {
                navController.popBackStack()
            }
        }
    }
}