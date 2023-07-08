package com.besho.authentication.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.besho.authentication.login.presentation.screen.login.LoginScreen
import com.besho.authentication.login.presentation.screen.login.navigation.LoginInternalNavigation
import com.besho.authentication.login.presentation.screen.login.viewmodel.LoginViewModel

fun NavGraphBuilder.authenticationGraph(
    navController: NavController,
    event: (AuthenticationExternalNavigation) -> Unit
) {
    navigation(
        route = "authentication",
        startDestination = AuthenticationScreen.Login.route
    ) {
        composable(route = AuthenticationScreen.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                externalNavigation = {
                    when (it) {
                        is AuthenticationExternalNavigation.HomeNavigation -> {
                            event.invoke(
                                AuthenticationExternalNavigation.HomeNavigation(
                                    "beshoy"
                                )
                            )
                        }
                    }
                },
                internalNavigation = {
                    when (it) {
                        is LoginInternalNavigation.ForgetPassword -> {
                            navController.navigate(AuthenticationScreen.ForgetPassword.route)
                        }

                        is LoginInternalNavigation.CreateAccount -> {
                            navController.navigate(AuthenticationScreen.Register.route)
                        }
                    }

                },
                loginViewModel
            )
        }
        composable(route = AuthenticationScreen.Register.route) {

        }
        composable(route = AuthenticationScreen.ForgetPassword.route) {}
    }
}

sealed class AuthenticationScreen(
    val route: String
) {
    object Login : AuthenticationScreen("login_Screen")
    object Register : AuthenticationScreen("register_screen")
    object ForgetPassword : AuthenticationScreen("forget_password_screen")
    object Splash : AuthenticationScreen("register_screen")
}
