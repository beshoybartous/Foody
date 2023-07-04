package com.besho.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.besho.home.DashBoardScreen

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    event: (HomeExternalNavigation) -> Unit

) {
    navigation(
        route = "home",
        startDestination = HomeScreens.Dashboard.route
    ) {

        composable(route = HomeScreens.Dashboard.route) {
            DashBoardScreen()
        }
    }
}

sealed class HomeScreens(
    val route: String
) {
    object Dashboard : HomeScreens("dashboard_screen")
}

fun NavController.navigateToHomeScreen() {
    this.navigate(HomeScreens.Dashboard.route)
}
