package com.besho.foody

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.besho.authentication.navigation.AuthenticationExternalNavigation
import com.besho.authentication.navigation.authenticationGraph
import com.besho.foody.ui.theme.FoodyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodyTheme {
                navController = rememberNavController()
                SetupNavGraph(navController)
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController,
        startDestination = "authentication",
        builder = {
            authenticationGraph(navController) {
                when (it) {
                    is AuthenticationExternalNavigation.HomeNavigation -> {
//                        navController.navigateToHomeScreen()
                    }
                }
            }
//            homeGraph(navController){
//                when(it){
//                    is HomeExternalNavigation.MINavigation->{
//
//                    }
//                }
//            }
        })
}