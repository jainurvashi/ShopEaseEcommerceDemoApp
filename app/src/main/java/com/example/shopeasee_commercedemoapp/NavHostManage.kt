package com.example.shopeasee_commercedemoapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.shopeasee_commercedemoapp.screen.CartScreen
import com.example.shopeasee_commercedemoapp.screen.DetailActivity
import com.example.shopeasee_commercedemoapp.screen.ProductListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            ProductListScreen(
                navController = navController
            )
        }
        composable("details") {
            DetailActivity()
        }
        composable("CartScreen") {
            CartScreen(navController)
        }
    }
}

