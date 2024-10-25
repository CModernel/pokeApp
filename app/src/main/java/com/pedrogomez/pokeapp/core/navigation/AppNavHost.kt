package com.pedrogomez.pokeapp.core.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pedrogomez.pokeapp.main_menu.presentation.MainMenuEntryScreen
import com.pedrogomez.pokeapp.pokedex.presentation.PokedexEntryScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenRoute.MainMenu.route
    ) {
        composable(ScreenRoute.MainMenu.route) {
            MainMenuEntryScreen(
                onNavigateTo = { route -> navController.navigate(route) }
            )
        }
        composable(ScreenRoute.Pokedex.route) {
            PokedexEntryScreen(
                onPokemonClick = {},
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(ScreenRoute.CloseApp.route) {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                (context as? Activity)?.finishAffinity()
            }
        }
    }
}

// Screen.kt
sealed class ScreenRoute(val route: String) {
    object MainMenu : ScreenRoute("mainMenu")
    object Pokedex : ScreenRoute("pokedex")
    object CloseApp : ScreenRoute("closeApp")
}