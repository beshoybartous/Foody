package com.besho.authentication.navigation

sealed class AuthenticationExternalNavigation {
    data class HomeNavigation(val userName: String) : AuthenticationExternalNavigation()
}