package com.besho.authentication.login.domain.usecase

import com.besho.authentication.login.domain.model.LoginValidationState

class LoginValidationsUseCase(
    private val userNameValidationUseCase: UserNameValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
) {
    operator fun invoke(
        username: String,
        password: String
    ): LoginValidationState {
        val isUserNameValid = userNameValidationUseCase(username)
        val isPasswordValid = passwordValidationUseCase(password)
        val loginValidationState = LoginValidationState()
        if (!isUserNameValid)
            loginValidationState.invalidUserName = true

        if (!isPasswordValid)
            loginValidationState.invalidPassword = true
        return loginValidationState
    }
}