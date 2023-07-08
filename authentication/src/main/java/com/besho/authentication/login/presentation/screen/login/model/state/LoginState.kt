package com.besho.authentication.login.presentation.screen.login.model.state

import com.besho.authentication.R

data class LoginState(
    val username: String = "",
    val password: String = "",
    val userNameError: Int = R.string.login_invalid_username,
    val passwordError: Int = R.string.login_invalid_password,
    val isLoading: Boolean = false,
    val isUserNameInvalid: Boolean = false,
    val isPasswordInvalid: Boolean = false,
)