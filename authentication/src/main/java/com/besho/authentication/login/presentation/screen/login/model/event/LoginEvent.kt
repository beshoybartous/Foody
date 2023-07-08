package com.besho.authentication.login.presentation.screen.login.model.event

sealed class LoginEvent {
    data class UpdateUserName(val userName: String) : LoginEvent()
    data class UpdatePassword(val password: String) : LoginEvent()
    object Login : LoginEvent()
}