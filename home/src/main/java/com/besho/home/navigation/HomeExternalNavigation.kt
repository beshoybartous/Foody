package com.besho.home.navigation


sealed class HomeExternalNavigation {
    data class MINavigation(val userName: String) : HomeExternalNavigation()
}