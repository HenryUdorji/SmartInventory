package com.smartinventory.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.smartinventory.app.ui.screens.dashboard.DashboardScreen
import com.smartinventory.app.ui.screens.itemdetail.ItemDetailScreen
import com.smartinventory.app.ui.screens.itemedit.ItemEditScreen
import com.smartinventory.app.ui.screens.itemlist.ItemListScreen
import com.smartinventory.app.ui.screens.reports.ReportsScreen

/**
 * @Author: ifechukwu.udorji
 * @Date: 4/10/2025
 */
sealed class Screen(val route: String) {
    data object Dashboard : Screen("dashboard")
    data object ItemList : Screen("items")
    data object ItemDetail : Screen("items/{itemId}") {
        fun createRoute(itemId: Int) = "items/$itemId"
    }
    data object ItemEdit : Screen("items/{itemId}/edit") {
        fun createRoute(itemId: Int) = "items/$itemId/edit"
    }
    data object ItemAdd : Screen("items/add")
    data object Reports : Screen("reports")
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Dashboard.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onNavigateToItems = {
                    navController.navigate(Screen.ItemList.route)
                },
                onNavigateToReports = {
                    navController.navigate(Screen.Reports.route)
                }
            )
        }

        composable(Screen.ItemList.route) {
            ItemListScreen(
                onItemClick = { itemId ->
                    navController.navigate(Screen.ItemDetail.createRoute(itemId))
                },
                onAddItemClick = {
                    navController.navigate(Screen.ItemAdd.route)
                },
            )
        }

        composable(
            route = Screen.ItemDetail.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            ItemDetailScreen(
                itemId = itemId,
                onBackClick = {
                    navController.popBackStack()
                },
                onEditClick = { id ->
                    navController.navigate(Screen.ItemEdit.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.ItemEdit.route,
            arguments = listOf(
                navArgument("itemId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            ItemEditScreen(
                itemId = itemId,
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ItemAdd.route) {
            ItemEditScreen(
                itemId = -1, // -1 indicates a new item
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Reports.route) {
            ReportsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}