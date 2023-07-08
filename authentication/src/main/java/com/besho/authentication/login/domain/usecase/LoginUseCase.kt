package com.besho.authentication.login.domain.usecase

import com.besho.authentication.login.domain.model.LoginResult
import com.besho.authentication.login.domain.repo.AuthenticationRepo

class LoginUseCase(
    private val authenticationRepo: AuthenticationRepo
) {

    suspend operator fun invoke(
        username: String,
        password: String
    ): LoginResult =
        authenticationRepo.signIn(username, password)
}