package com.besho.authentication.login.presentation.screen.login.navigation



sealed class LoginInternalNavigation {
    object ForgetPassword : LoginInternalNavigation()
    object CreateAccount : LoginInternalNavigation()
}