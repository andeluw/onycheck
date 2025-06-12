package com.project.onycheck.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.onycheck.ui.navigation.NavItem
import com.project.onycheck.ui.theme.Blue100
import com.project.onycheck.ui.theme.Blue200
import com.project.onycheck.ui.theme.Blue300
import com.project.onycheck.ui.theme.Blue500
import com.project.onycheck.ui.theme.Blue600
import com.project.onycheck.ui.theme.Blue700
import com.project.onycheck.ui.theme.Blue800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navController: NavController,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val items = listOf(
        NavItem.Home,
        NavItem.Analyze,
        NavItem.News,
        NavItem.Doctors
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = items.any { currentDestination?.hierarchy?.any { dest -> dest.route == it.route } ?: false }

    Scaffold(
        topBar = topBar,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Blue800
                ) {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon!!,
                                    contentDescription = screen.title,
                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true)
                                        Blue100
                                    else
                                        Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                screen.navigate(navController)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Blue100,
                                unselectedIconColor = Color.White,
                                indicatorColor = Blue600
                            )
                        )
                    }
                }
            }
        },
        content = content
    )
}