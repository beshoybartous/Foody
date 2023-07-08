package com.besho.authentication.login.domain.model

sealed class LoginResult {
    data class OnError(
        val errorMessage: Int,
    ) : LoginResult()

    object OnSuccess : LoginResult()
}