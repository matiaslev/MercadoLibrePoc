package com.matiaslev.mercadolibrepoc

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.matiaslev.mercadolibrepoc.screens.Details
import com.matiaslev.mercadolibrepoc.screens.Home
import com.matiaslev.mercadolibrepoc.screens.Search
import com.matiaslev.mercadolibrepoc.ui.theme.MercadoLibrePocTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercadoLibrePocTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    private fun MyApp() {
        val navController = rememberNavController()

        NavHost(navController, startDestination = "home") {
            composable("home") {
                Home(navController, model)
            }
            composable("search") {
                Search(navController, model)
            }
            composable("details/{link}") { backStackEntry ->
                backStackEntry.arguments?.getString("link")?.let { link ->
                    Details(link)
                }
            }
        }
    }
}